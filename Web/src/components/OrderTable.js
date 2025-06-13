import React, { useEffect } from 'react';
import { toast } from 'react-toastify';
import '../styles/OrderTable.css';

const OrderRow = ({ order }) => {
    useEffect(() => {
        const id = order?.id;
        if (!id) return;

        const socket = new WebSocket(`ws://localhost:9095/ws/order-status?orderId=${id}`);

        socket.onopen = () => console.log(`Connected to order #${id}`);

        socket.onmessage = (event) => {
            const status_raw = event.data;
            var status = ""

            if (status_raw == "CANCELLED") {
                status = "Отменен"
            } else if (status_raw == "FINISHED") {
                status = "Оплачен"
            } else {
                status = status_raw
            }

            const ret_st = status
            const div = document.createElement('div');
            div.innerText = `У заказа ${id} новый статус: ${ret_st}`;
            div.style.cssText = `
                position: fixed;
                bottom: 20px;
                right: 20px;
                background: #007bff;
                color: white;
                padding: 10px;
                border-radius: 4px;
                z-index: 9999;
                width: 500px;
                height: 100px
                height: auto;
            `;
            document.body.appendChild(div);
            setTimeout(() => div.remove(), 5000);
        };

        // socket.onmessage = (event) => {
        //     const status = event.data;
        //     // console.log(`Got message for order #${id}: ${status}`);
        //     // toast.info(`Order #${id} updated: ${status}`, {
        //     //     position: "bottom-right",
        //     //     autoClose: 10000,
        //     // });
        //     console.log("Order status:", status);
        //     // toast.info(`Order status: ${status}`);
        //     // console.log("Order status:", status);
        //
        //     // socket.onmessage = (event) => {
        //     //     const status = event.data;
        //     //     console.log("Order status:", status);
        //     //     alert(`Order status: ${status}`);
        //     // };
        //
        //
        // };
        socket.onerror = (e) => console.error(`WebSocket error for order #${id}:`, e);
        socket.onclose = () => console.log(`Closed connection for order #${id}`);

        return () => socket.close();
    }, [order?.id]);

    if (!order || typeof order !== 'object') {
        console.warn("Skipping invalid order:", order);
        return null;
    }

    return (
        <tr>
            <td>{order.id}</td>
            <td>{order.status}</td>
            <td>{order.amount}</td>
            <td>{order.payload}</td>
        </tr>
    );
};


const OrderTable = ({ orders }) => {
    return (
        <div className="table-container">
            <table className="order-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Статус</th>
                    <th>Сумма</th>
                    <th>Описание</th>
                </tr>
                </thead>
                <tbody>
                {orders?.map(order => (
                    order?.id ? <OrderRow key={order.id} order={order} /> : null
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrderTable;
