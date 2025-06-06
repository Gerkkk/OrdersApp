import React, { useEffect, useState } from 'react';
import { getBalance, createAccount, deposit } from '../api';
import Header from '../components/Header';
import "../styles/Form.css";

const AccountPage = ({ userId }) => {
    const [balance, setBalance] = useState(null);
    const [depositAmount, setDepositAmount] = useState('');

    const loadBalance = async () => {
        const b = await getBalance(userId);
        setBalance(b);
    };

    useEffect(() => {
        loadBalance();
    }, []);

    const handleCreateAccount = async () => {
        await createAccount(userId);
        loadBalance();
    };

    const handleDeposit = async () => {
        await deposit(userId, parseFloat(depositAmount));
        setDepositAmount('');
        loadBalance();
    };

    return (
        <div>
            <Header userId={userId} />
            <div className="form-container">
                <h2 style={{ textAlign: 'center' }}>💵 Счет</h2>
                {balance === -1 ? (
                    <>
                        <p style={{ textAlign: 'center' }}>😢 Счета нет</p>
                        <button className="btn" onClick={handleCreateAccount}>🏦 Создать счет</button>
                    </>
                ) : (
                    <>
                        <p style={{ textAlign: 'center' }}>💰Баланс: {balance}</p>
                        <input
                            placeholder="Сумма пополнения"
                            value={depositAmount}
                            onChange={(e) => setDepositAmount(e.target.value)}
                        />
                        <button className="btn" onClick={handleDeposit}>💸 Пополнить</button>
                    </>
                )}
            </div>
        </div>
    );
};

export default AccountPage;
