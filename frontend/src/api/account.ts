import type { CustomerDTO, BankcardDTO, UpdateInfoDTO, BankcardVO } from '@/types/account';

// 模拟客户数据
const mockCustomers: CustomerDTO[] = [
    {
        phoneNumber: '13345678900',
        riskLevel: 3,
        name: '张三',
        password: '123456',
        idNumber: '110101199001011234'
    }
];

// 模拟银行卡数据
const mockBankcards: Record<string, BankcardVO[]> = {
    'A123456789': [
        { tradingAccount: 'T10001', bankcardNumber: '6225880123456789' },
        { tradingAccount: 'T10002', bankcardNumber: '6228481234567890123' }
    ],
    'B987654321': [
        { tradingAccount: 'T20001', bankcardNumber: '6226098765432100' }
    ]
};

// 模拟交易账户数据
const mockTradingAccounts: Record<string, string[]> = {
    'A123456789': ['T10001', 'T10002'],
    'B987654321': ['T20001', 'T20002']
};

// 创建账户
export function createAccountService(data: CustomerDTO) {
    mockCustomers.push(data);
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: {
            fundAccount: `A${Math.floor(Math.random() * 1000000000)}`
        }
    });
}

// 添加银行卡
export function addBankcardService(data: BankcardDTO) {
    const { fundAccount, bankcardNumber } = data;
    const newTradingAccount = `T${Math.floor(Math.random() * 100000)}`;

    if (!mockBankcards[fundAccount]) {
        mockBankcards[fundAccount] = [];
    }

    mockBankcards[fundAccount].push({
        tradingAccount: newTradingAccount,
        bankcardNumber
    });

    return Promise.resolve({
        code: 0,
        message: 'success',
        data: { tradingAccount: newTradingAccount }
    });
}

// 更新信息
export function UpdateInfoService(data: UpdateInfoDTO) {
    const customer = mockCustomers.find(c => c.phoneNumber === data.phoneNumber);
    if (customer) {
        customer.riskLevel = data.riskLevel;
    }
    return Promise.resolve({
        code: 0,
        message: 'success'
    });
}

// 获取银行卡列表
export function getBankcardsService(fundAccount: string) {
    const cards = mockBankcards[fundAccount] || [];
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: cards
    });
}

// 删除银行卡
export function deleteBankcardService(tradingAccountId: string) {
    for (const fundAccount in mockBankcards) {
        mockBankcards[fundAccount] = mockBankcards[fundAccount].filter(
            card => card.tradingAccount !== tradingAccountId
        );
    }
    return Promise.resolve({
        code: 0,
        message: 'success'
    });
}

// 获取交易账户列表
export function getTradingAccountsService(fundAccount: string) {
    const accounts = mockTradingAccounts[fundAccount] || [];
    return Promise.resolve({
        code: 0,
        message: 'success',
        data: accounts
    });
}
