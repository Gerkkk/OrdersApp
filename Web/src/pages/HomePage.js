import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Form.css'; // Подключение CSS

const HomePage = ({ setUserId }) => {
    const [idInput, setIdInput] = useState('');
    const navigate = useNavigate();

    const handleEnter = () => {
        setUserId(idInput);
        navigate('/orders');
    };

    return (
        <div className="page-center">
            <div className="form-container">
                <h1 style={{ textAlign: 'center' }}>Добро пожаловать!</h1>
                <input
                    placeholder="Введите ID пользователя"
                    value={idInput}
                    onChange={(e) => setIdInput(e.target.value)}
                />
                <button className="btn" onClick={handleEnter}>
                    Войти
                </button>
            </div>
        </div>
    );
};

export default HomePage;
