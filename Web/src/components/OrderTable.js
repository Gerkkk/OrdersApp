import React from 'react';
import '../styles/OrderTable.css';

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
                {orders.map(o => (
                    <tr key={o.id}>
                        <td>{o.id}</td>
                        <td>{o.status}</td>
                        <td>{o.amount}</td>
                        <td>{o.payload}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrderTable;
