import React, { useState } from 'react';
import { createOrder } from '../api';

const OrderForm = ({ userId, onCreated }) => {
    const [amount, setAmount] = useState('');
    const [payload, setPayload] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        await createOrder({
            userId: Number(userId),
            amount: Number(amount),
            payload,
        });
        onCreated();
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Сумма" value={amount} onChange={(e) => setAmount(e.target.value)} required />
            <input placeholder="Описание (JSON)" value={payload} onChange={(e) => setPayload(e.target.value)} required />
            <button className="btn" type="submit">Создать заказ</button>
        </form>
    );
};

export default OrderForm;
