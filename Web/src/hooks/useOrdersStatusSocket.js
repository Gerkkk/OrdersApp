import { useEffect, useRef } from "react";
import { toast } from "react-toastify";

export const useOrdersStatusSocket = (orders) => {
    const socketsRef = useRef({});

    useEffect(() => {
        if (!orders || orders.length === 0) return;

        orders.forEach((order) => {
            const id = order.id;
            if (socketsRef.current[id]) return; // уже подписан

            const socket = new WebSocket(`ws://localhost:9093/ws/order-status?orderId=${id}`);

            socket.onmessage = (event) => {
                const newStatus = event.data;
                toast.info(`Статус заказа #${id} обновлён: ${newStatus}`, {
                    position: "bottom-right",
                    autoClose: 10000,
                });
            };

            socket.onopen = () => {
                console.log(`🧩 WS открыт для заказа #${id}`);
            };

            socket.onerror = (e) => {
                console.warn(`⚠️ WS ошибка заказа #${id}`, e);
            };

            socket.onclose = () => {
                console.log(`🔌 WS закрыт для заказа #${id}`);
            };

            socketsRef.current[id] = socket;
        });

        return () => {
            Object.values(socketsRef.current).forEach((s) => s.close());
        };
    }, [orders]);
};
