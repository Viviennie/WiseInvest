import type { Product } from '@/types/product';

// 模拟产品数据 - 严格匹配 Product 接口
const mockProducts: Product[] = [
    {
        productId: 1,
        productName: '稳健增长债券',
        productType: '债券型',
        riskLevel: 2,
        productStatus: 1
    },
    {
        productId: 2,
        productName: '蓝筹股精选',
        productType: '股票型',
        riskLevel: 4,
        productStatus: 1
    },
    {
        productId: 3,
        productName: '科技成长混合',
        productType: '混合型',
        riskLevel: 3,
        productStatus: 0
    },
    {
        productId: 4,
        productName: '货币市场基金',
        productType: '货币型',
        riskLevel: 1,
        productStatus: 1
    },
    {
        productId: 5,
        productName: '国际配置QDII',
        productType: 'QDII',
        riskLevel: 5,
        productStatus: 1
    }
];

// 模拟净值数据
const mockNetValues: Record<number, Record<string, number>> = {
    1: { '2023-05-01': 1.052, '2023-05-02': 1.053, '2023-05-03': 1.055 },
    2: { '2023-05-01': 1.215, '2023-05-02': 1.198, '2023-05-03': 1.223 },
    3: { '2023-05-01': 1.086, '2023-05-02': 1.092, '2023-05-03': 1.104 },
    4: { '2023-05-01': 1.001, '2023-05-02': 1.002, '2023-05-03': 1.002 },
    5: { '2023-05-01': 1.125, '2023-05-02': 1.118, '2023-05-03': 1.131 }
};

// 模拟交易日期
const mockTransactionDates = ['2023-05-01', '2023-05-02', '2023-05-03'];

// 获取产品列表 (带分页)
export function fetchProducts(page = 1, pageSize = 10) {
    const start = (page - 1) * pageSize;
    const end = start + pageSize;
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: {
            records: mockProducts.slice(start, end),
            total: mockProducts.length,
            size: pageSize,
            current: page
        }
    });
}

// 搜索产品 (按产品名称或类型)
export function searchProducts(keyword: string) {
    const filtered = mockProducts.filter(product =>
        product.productName.includes(keyword) ||
        product.productType.includes(keyword)
    );
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: filtered
    });
}

// 获取产品净值
export function fetchNetValue(productId: number, date: string) {
    const value = mockNetValues[productId]?.[date];

    if (value !== undefined) {
        return Promise.resolve({
            code: 0,
            message: 'success',
            data: {
                productId,
                date,
                netValue: value,
                growthRate: +(value / mockNetValues[productId][getPreviousDate(date)] - 1).toFixed(4)
            }
        });
    }
    return Promise.reject({
        code: 404,
        message: '净值数据不存在'
    });
}

// 获取交易日期
export function fetchTransactionDate() {
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: mockTransactionDates
    });
}

// 辅助函数：获取前一天日期
function getPreviousDate(currentDate: string): string {
    const date = new Date(currentDate);
    date.setDate(date.getDate() - 1);
    return date.toISOString().split('T')[0];
}
