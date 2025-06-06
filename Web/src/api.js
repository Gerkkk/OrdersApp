import config from './config';

const base = config.apiBaseUrl;

export const getOrders = async (userId) => {
    const res = await fetch(`${base}/api/orders/list/${userId}`);
    return res.json();
};

export const createOrder = async (order) => {
    const res = await fetch(`${base}/api/orders/`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            accept: '*/*',
        },
        body: JSON.stringify(order),
    });
    return res.json();
};

export const getBalance = async (userId) => {
    const res = await fetch(`${base}/api/accounts/balance/${userId}`);
    return res.json();
};

export const createAccount = async (userId) => {
    const res = await fetch(`${base}/api/accounts/${userId}`, {
        method: 'POST',
        headers: { accept: '*/*' },
    });
    return res;
};

export const deposit = async (userId, amount) => {
    const res = await fetch(`${base}/api/accounts/deposit/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            accept: '*/*',
        },
        body: JSON.stringify({ amount }),
    });
    return res;
};
