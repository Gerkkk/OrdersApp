import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const Header = ({ userId }) => {
    const navigate = useNavigate();
    const location = useLocation();

    const buttonStyle = {
        backgroundColor: '#007bff',
        color: '#fff',
        border: 'none',
        padding: '8px 16px',
        borderRadius: '6px',
        fontSize: '16px',
        cursor: 'pointer',
        transition: 'background-color 0.3s ease',
        marginRight: '10px'
    };

    const headerStyle = {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 20px',
        backgroundColor: '#fff',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    };

    const userIdStyle = {
        cursor: 'pointer',
        fontWeight: 'bold',
        fontSize: '16px',
        color: '#333',
    };

    return (
        <header style={headerStyle}>
            <div>
                <button style={buttonStyle} onClick={() => navigate('/')}>🚪 Выйти</button>

                {location.pathname === '/account' && (
                    <button style={buttonStyle} onClick={() => navigate('/orders')}>
                        📦 Заказы
                    </button>
                )}

                {location.pathname === '/orders' && (
                    <button style={buttonStyle} onClick={() => navigate('/account')}>
                        💼 Счет
                    </button>
                )}
            </div>

            <div onClick={() => navigate('/account')} style={userIdStyle}>
                🗿 ID: {userId}
            </div>
        </header>
    );
};

export default Header;
