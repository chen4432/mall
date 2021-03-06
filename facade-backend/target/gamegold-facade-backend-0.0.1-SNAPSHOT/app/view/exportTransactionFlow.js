/*
 * 交易流水导出页面
 */
Ext.define('MyApp.view.exportTransactionFlow', {
    extend: 'Ext.panel.Panel',
    id: 'exportTransactionFlow',
    closable: true,
    title: '交易流水导出',
    autoScroll: false,
    listeners: {
        'resize': function () {
            this.orderGrid.setHeight(window.document.body.offsetHeight - 325);
        }
    },
    toolbar: null,
    getToolbar: function () {
        var me = this;
        if (Ext.isEmpty(me.toolbar)) {
            me.toolbar = Ext.widget('toolbar', {
                dock: 'top',
                items: [{
                    text: '导出',
                    listeners: {
                        click: {
                            fn: me.exportOrder,
                            scope: me
                        }
                    }
                }]
            });
        }
        return me.toolbar;
    },
    // 导出订单
    exportOrder: function (button, e, eOpts) {
        var me = this, url,
            queryForm = me.getQueryForm(),
            values = queryForm.getValues();
        url = './funds/exportTransactionFlow.action?' + Ext.urlEncode(values);
        window.open(url);
    },
    store: null,
    getStore: function () {
        var me = this;
        if (me.store == null) {
            me.store = Ext.create('MyApp.store.TransactionStore', {
                autoLoad: true,
                listeners: {
                    beforeload: function (store, operation, eOpts) {
                        var queryForm = me.getQueryForm(),
                            values = queryForm.getValues();
                        if (queryForm != null) {
                            Ext.apply(operation, {
                                params: {
                                    'servicerId': values.servicerId,
                                    'loginAccount': values.loginAccount,
                                    'orderState': values.orderState,
                                    'statementStartTime': values.statementStartTime,
                                    'statementEndTime': values.statementEndTime,
                                    'refererType': values.refererType,
                                    'goodsTypeId': values.goodsTypeId,
                                    'goodsTypeName': values.goodsTypeName
                                }
                            });
                        }
                    }
                }
            });
        }
        return me.store;
    },
    pagingToolbar: null,
    getPagingToolbar: function () {
        var me = this;
        if (me.pagingToolbar == null) {
            me.pagingToolbar = Ext.widget('pagingtoolbar', {
                store: me.getStore(),
                dock: 'bottom',
                displayInfo: true
            });
        }
        return this.pagingToolbar;
    },
    orderGrid: null,
    getOrderGrid: function () {
        var me = this;
        if (Ext.isEmpty(me.orderGrid)) {
            me.orderGrid = Ext.widget('gridpanel', {
                header: false,
                columnLines: true,
                store: me.getStore(),
                columns: [{
                    xtype: 'rownumberer'
                }, {
                    dataIndex: 'serviceAccount',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '客服账号'
                }, {
                    dataIndex: 'loginAccount',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '卖家账号'
                }, {
                    dataIndex: 'orderId',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '订单号'
                }, {
                    dataIndex: 'orderState',
                    text: '订单状态 ',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                        return DataDictionary.rendererSubmitToDisplay(value, 'orderState');
                    }
                },{
                    dataIndex: 'goodsTypeName',
                    text: '商品类型 ',
                    flex: 1,
                    sortable: false,
                    align: 'center'
                }, {
                    dataIndex: 'title',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '商品名称'
                }, {
                    dataIndex: 'gameName',
                    flex: 1.5,
                    sortable: false,
                    align: 'center',
                    renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                        var gameProp = record.get('gameName') + "/"
                            + record.get('region') + "/"
                            + record.get('server');
                        if (Ext.isEmpty(record.get('gameRace'))) {
                            return gameProp;
                        } else {
                            return gameProp + '/' + record.get('gameRace');
                        }
                    },
                    text: '下单游戏/区/服'
                }, {
                    dataIndex: 'serviceGameProp',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '发货游戏/区/服'
                }, {
                    dataIndex: 'refererType',
                    text: '订单来源 ',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                        if (value == 1 || value == 2) {
                            return '金币商城内部';
                        } else {
                            return DataDictionary.rendererSubmitToDisplay(value, 'refererTypeName');
                        }
                    }
                }, {
                    dataIndex: 'orderUnitPrice',
                    flex: 1,
                    sortable: false,
                    renderer: function (v) {
                        return Ext.util.Format.currency(v, '￥', 4);
                    },
                    align: 'center',
                    text: '商品单价'
                }, {
                    dataIndex: 'goldCount',
                    flex: 1,
                    sortable: false,
                    align: 'center',
                    text: '购买总数</br>(金币数) '
                }, {
                    dataIndex: 'orderTotalPrice',
                    flex: 1,
                    renderer: function (v) {
                        return Ext.util.Format.currency(v, '￥', 2);
                    },
                    sortable: false,
                    align: 'center',
                    text: '订单总<br/>金额'
                }, {
                    dataIndex: 'endTime',
                    xtype: 'datecolumn',
                    format: 'Y-m-d H:i:s',
                    flex: 1.5,
                    align: 'center',
                    sortable: false,
                    text: '完成时间'
                }
                ],
                plugins: [{
                    ptype: 'gamegoldrowexpander',
                    header: true,
                    rowsExpander: false,
                    expandOnDblClick: false,
                    rowBodyElement: 'MyApp.order.OrderConfigPanel'
                }],
                dockedItems: [me.getToolbar(), me.getPagingToolbar()],
                selModel: Ext.create('Ext.selection.CheckboxModel', {
                    allowDeselect: true,
                    mode: 'SINGLE'
                })
            });
        }
        return me.orderGrid;
    },
    queryForm: null,
    getQueryForm: function () {
        var me = this;
        if (me.queryForm == null) {
            me.queryForm = Ext.widget('form', {
                layout: 'column',
                defaults: {
                    margin: '10 10 10 10',
                    columnWidth: .25,
                    labelWidth: 100,
                    xtype: 'textfield'
                },
                items: [{
                    fieldLabel: '客服账户',
                    hidden: CurrentUser.getUserTypeCode() != 3,
                    xtype: 'commonuserselector',
                    columnWidth: .2,
                    labelWidth: 100,
                    name: 'servicerId'
                }, {
                    fieldLabel: '卖家5173账号',
                    name: 'loginAccount',
                    columnWidth: .2,
                }, DataDictionary.getDataDictionaryCombo('orderState', {
                    fieldLabel: '订单状态',
                    columnWidth: .2,
                    labelWidth: 100,
                    name: 'orderState',
                    editable: false
                }, {value: null, display: '全部'})
                    , Ext.create('Ext.form.ComboBox', {
                        fieldLabel: '商品类型',
                        columnWidth: .2,
                        name: 'goodsTypeName',
                        store: Ext.create('MyApp.store.MallGoodsTypeNameIdStore', {
                            listeners: {
                                load: function (store, records, successful, eOpts) {
                                    //添加
                                    store.insert(0,{id:0, name:'全部'});
                                }
                            }
                        }),
                        displayField: 'name',
                        valueField: 'name',
                        value:'全部',
                        editable: false
                    }),DataDictionary.getDataDictionaryCombo('refererTypeName', {
                        fieldLabel: '订单来源',
                        columnWidth: .2,
                        labelWidth: 100,
                        name: 'refererType',
                        editable: false
                    }, {value: null, display: '全部'})
                    , {
                        fieldLabel: '交易日期',
                        columnWidth: .5,
                        xtype: 'rangedatefield',
                        //起始日期组件的name属性。
                        fromName: 'statementStartTime',
                        //终止日期组件的name属性。
                        toName: 'statementEndTime',
                        //起始日期组件的初始值。
                        fromValue: new Date(),
                        //终止日期组件的初始值。
                        toValue: new Date()
                    },  {
                        xtype: 'panel',
                        width: .4,
                        height: 25,
                        border: 0
                    }, {
                        xtype: 'panel',
                        width: .5,
                        height: 25,
                        border: 0
                    }, {
                        xtype: 'checkbox',
                        boxLabel: '是否汇总',
                        columnWidth: 0.09,
                        inputValue: '1',
                        labelWidth: 80,
                        id: 'isCount',
                        checked: false
                    }, {
                        fieldLabel: '汇总笔数',
                        columnWidth: 0.2,
                        id: 'totalCounts',
                        labelWidth: 70,
                        readOnly: true
                        //style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 1px solid;'
                    }, {
                        fieldLabel: '汇总流水',
                        columnWidth: 0.2,
                        id: 'totalAmount',
                        labelWidth: 70,
                        readOnly: true
                        //style:'border-right:0px; border-top:0px; border-left:0px; border-bottom: #000000 1px solid;'
                    }, {
                        fieldLabel: '汇总收入',
                        id: 'income',
                        columnWidth: 0.49,
                        labelWidth: 70,
                        readOnly: true,
                        margin: '10px 300px 10px 10px'
                        //style:'border-right:0px; border-top:0px; border-left:0px; border-bottom: #000000 1px solid;'
                    }, {
                        fieldLabel: '红包数量',
                        id: 'redEnvelope',
                        columnWidth: 0.29,
                        labelWidth: 70,
                        readOnly: true,
                        margin: '10px 10px 10px 105px'
                    }, {
                        fieldLabel: '红包金额汇总',
                        id: 'redEnvelopeAccount',
                        columnWidth: 0.2,
                        labelWidth: 90,
                        readOnly: true
                    }, {
                        fieldLabel: '优惠券数量',
                        id: 'shopCouponCount',
                        columnWidth: 0.21,
                        labelWidth: 80,
                        readOnly: true
                    }, {
                        fieldLabel: '优惠券金额汇总',
                        id: 'shopCouponAccount',
                        columnWidth: 0.25,
                        labelWidth: 110,
                        readOnly: true
                    }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        me.getQueryForm().getForm().reset();
                    }
                }, '->', {
                    text: '查询',
                    handler: function () {
                        me.getPagingToolbar().moveFirst();

                        var isCount = Ext.getCmp('isCount').getValue();

                        if (isCount) {
                            //获取更新数据
                            var queryForm = me.getQueryForm(),
                                values = queryForm.getValues();
                            Ext.Ajax.request({
                                url: './funds/statisticOrderInfo.action',

                                params: {
                                    orderState: values.orderState,
                                    statementStartTime: values.statementStartTime,
                                    statementEndTime: values.statementEndTime,
                                    refererType: values.refererType
                                },
                                success: function (response, opts) {
                                    var json = Ext.decode(response.responseText);
                                    //queryForm = me.getQueryForm().getForm();
                                    Ext.getCmp('totalCounts').setValue(json.totalCounts);
                                    Ext.getCmp('totalAmount').setValue(json.totalAmount);
                                    Ext.getCmp('income').setValue(json.income);
                                    Ext.getCmp('redEnvelope').setValue(json.redEnvelope);
                                    Ext.getCmp('redEnvelopeAccount').setValue(json.redEnvelopeAccount);
                                    Ext.getCmp('shopCouponCount').setValue(json.shopCouponCount);
                                    Ext.getCmp('shopCouponAccount').setValue(json.shopCouponAccount);
                                },
                                exception: function (response, opts) {
                                    var json = Ext.decode(response.responseText);
                                    Ext.ux.Toast.msg("温馨提示", json.message);
                                }
                            });
                        }
                    }
                }]
            });
        }
        return this.queryForm;
    },
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getQueryForm(), me.getOrderGrid()]
        });
        me.callParent(arguments);
    }
});