import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './styles/App.css';

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <>
        <App />
        <ToastContainer position="bottom-right" autoClose={10000} />
    </>
);
