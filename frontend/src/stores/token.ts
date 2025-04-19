// useTokenStore.ts - 静态数据版本
import { ref } from 'vue';

// 模拟 token 存储
const mockToken = ref('');

export const useTokenStore = () => {
    // 设置 token
    const setToken = (newToken: string) => {
        mockToken.value = newToken;
        console.log('[Mock] Token set:', newToken); // 添加日志便于调试
    };

    // 移除 token
    const removeToken = () => {
        console.log('[Mock] Token removed'); // 添加日志便于调试
        mockToken.value = '';
    };

    return {
        token: mockToken,
        setToken,
        removeToken
    };
};

// 初始时可设置一个测试token（可选）
// useTokenStore().setToken('test-static-token-123');
