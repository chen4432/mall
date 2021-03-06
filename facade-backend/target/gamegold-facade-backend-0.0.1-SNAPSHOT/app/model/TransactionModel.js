/*
 * 交易流水信息
 */
Ext.define('MyApp.model.TransactionModel', {
    extend: 'Ext.data.Model',
    idProperty: 'extId',
    idgen: 'uuid',
    fields: [{
    	name: 'extId'
    },{
    	name: 'serviceAccount'//客服账号
    },{
    	name: 'loginAccount'//卖家账号
    },{
	    name: 'orderId'//订单号
	},{
    	name: 'orderState',//订单状态
    	type: 'int'
    },{
        name: 'refererType',//订单来源
        type: 'int'
    },/*{
        name: 'gameAccount'//成交游戏账号
    },*/{
        name: 'title'//商品名称
    },{
        name: 'gameName'//游戏名称
    },{
        name: 'region'//所在区
    },{
        name: 'server'//所在服
    },{
        name: 'gameRace'//所在阵营
    },{
        name: 'serviceGameProp'//发货游戏区服
    },{
        name: 'goodsTypeId',//商品类型id
        type: 'int'
    },{
        name: 'goodsTypeName'//商品类型名称
    },{
        name: 'orderUnitPrice',//商品单价(1金币对应多少人民币)
        type: 'float'
    },/*{
        name: 'repositoryUnitPrice',//库存单价(1金币对应多少人民币)
        type: 'float'
    },*/{
        name: 'goldCount',//购买总数(购买金币数)
        type: 'int'
    },{
    	name: 'orderTotalPrice',//订单总金额
    	type: 'float'
    },/*{
    	name: 'incomeTotalPrice',//卖家收益
    	type: 'float'
    },*/{
        name: 'endTime',//完成时间
        dateReadFormat: 'Y-m-dTH:i:s',
        type: 'date'
    },{
        name: 'payTime',//付款时间
        dateReadFormat: 'Y-m-dTH:i:s',
        type: 'date'
    }]
});