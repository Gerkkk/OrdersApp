import React, { useEffect, useState } from 'react';
import { getOrders } from '../api';
import Header from '../components/Header';
import OrderForm from '../components/OrderForm';
import OrderTable from "../components/OrderTable";
import {useOrdersStatusSocket} from "../hooks/useOrdersStatusSocket"
import '../styles/Form.css';

const OrdersPage = ({ userId }) => {
    const [orders, setOrders] = useState([]);

    const loadOrders = async () => {
        const data = await getOrders(userId);
        setOrders(data.orders);
    };

    useEffect(() => {
        loadOrders();
    }, []);

    useOrdersStatusSocket(orders);

    return (
        <div>
            <Header userId={userId} />
            <div className="form-container">
                <h2 className="section-title" style={{ textAlign: 'center' }}>📊 Заказы</h2>
                <OrderTable orders={orders} />
                <h3 className="section-title" style={{ textAlign: 'center' }}> 💻 Создать заказ</h3>
                <OrderForm userId={userId} onCreated={loadOrders} />
            </div>
        </div>
    );
};

export default OrdersPage;
