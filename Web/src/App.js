import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import OrdersPage from './pages/OrdersPage';
import AccountPage from './pages/AccountPage';
import './styles/App.css';


const App = () => {
    const [userId, setUserId] = useState('');

    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage setUserId={setUserId} />} />
                <Route path="/orders" element={<OrdersPage userId={userId} />} />
                <Route path="/account" element={<AccountPage userId={userId} />} />
            </Routes>
        </Router>
    );
};

export default App;
