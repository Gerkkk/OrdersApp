import React, { useEffect, useState, useCallback } from 'react';
import { getOrders } from '../api';
import Header from '../components/Header';
import OrderForm from '../components/OrderForm';
import OrderTable from "../components/OrderTable";
import '../styles/Form.css';

const OrdersPage = ({ userId }) => {
    const [orders, setOrders] = useState([]);

    const loadOrders = useCallback(async () => {
        try {
            const data = await getOrders(userId);
            setOrders(data.orders || []);
        } catch (error) {
            console.error("Failed to load orders:", error);
            setOrders([]);
        }
    }, [userId]);

    useEffect(() => {
        loadOrders();
    }, [loadOrders]);

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
