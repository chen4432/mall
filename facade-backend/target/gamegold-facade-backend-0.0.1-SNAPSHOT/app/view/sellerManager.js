Ext.define('MyApp.view.sellerGrid', {
    extend: 'Ext.grid.Panel',
    header: false,
    columnLines: true,
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			allowDeselect: true,
			mode: 'SINGLE'
		});
		me.columns = [{
			xtype: 'rownumberer',
			width:25
		},{
			dataIndex: 'isShieldedType',
			sortable: false,
			width:40,
			//align: 'center',
			text: '是否<br/>寄售',
			renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
				var isHelper=record.get("isHelper")
				return CommonFunction.renderImg(value,isHelper);
			}
		}
			//,{
	    //    dataIndex: 'manualStatus',
	    //    dataIndex: 'manualStatus',
	    //    sortable: false,
			//width:40,
			////align: 'center',
			//text: '人工<br/>库存',
	    //    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
         //   	return CommonFunction.rendererEnable(!value);
         //   }
	    //},{
	    //    dataIndex: 'isShielded',
	    //    sortable: false,
	    //    width:40,
			//align: 'center',
			//text: '是否<br/>屏蔽',
	    //    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
         //   	return CommonFunction.rendererEnable(!value);
         //   }
	    //}
			,{
	        dataIndex: 'messagePower',
	        sortable: false,
	        width:40,
			align: 'center',
			text: '短信<br/>权限',
	        renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
            	return CommonFunction.rendererEnable(!value);
            }
	    },{
	        dataIndex: 'loginAccount',
	        sortable: false,	
	        //flex: 1.5,
	        width:120,
	        align: 'center',
	        text: '卖家5173账号'
	    },{
	        dataIndex: 'name',
	        sortable: false,
	        //flex: 1,
	        width:70,
	        align: 'center',
	        text: '联系人'
	    },{
				dataIndex: 'shopName',
				sortable: false,
				//flex: 1,
				width:70,
				align: 'center',
				text: '店铺名称'
			},{
	        dataIndex: 'phoneNumber',
	        sortable: false,
	        //flex: 1,
	        width:100,
	        align: 'center',
	        text: '联系电话'
	    },{
	        dataIndex: 'qq',
	        sortable: false,
	        //flex: 1,
	        width:100,
	        align: 'center',
	        text: 'QQ'
	    },{
			dataIndex: 'sellerType',
			sortable: false,
			//flex: 1,
			width:80,
			renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
	        	return DataDictionary.rendererSubmitToDisplay(value,'sellerType');
	        },
	        align: 'center',
			text: '卖家类型'
		},/*{
	        dataIndex: 'gameAccount',
	        sortable: false,
	        flex: 1,
	        align: 'center',
	        text: '游戏账号'
	    },{
	        dataIndex: 'password',
	        sortable: false,
	        flex: 1,
	        align: 'center',
	        text: '游戏密码'
	    },{
	        dataIndex: 'gameName',
	        sortable: false,
	        flex: 1,
	        align: 'center',
	        text: '游戏名称'
	    },{
			dataIndex: 'region',
			sortable: false,
			text: '所在区',
			align: 'center',
			flex: 1
		},{
			dataIndex: 'server',
			sortable: false,
			flex: 1,
			align: 'center',
			text: '所在服'
		},{
			dataIndex: 'gameRace',
			sortable: false,
			flex: 1,
			align: 'center',
			text: '所在阵营'
		},*/{
			dataIndex: 'games',
			sortable: false,
			//flex: 2,
			width:130,
			align: 'center',
			text: '关联的游戏'
		},{
			dataIndex: 'checkState',
			sortable: false,
			//flex: 1,
			width:90,
			renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
	        	return DataDictionary.rendererSubmitToDisplay(value,'sellerState');
	        },
	        align: 'center',
			text: '审核状态'
		},{
				dataIndex: 'openShState',
				sortable: false,
				//flex: 1,
				width:90,
				renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
					return DataDictionary.rendererSubmitToDisplay(value,'openShState');
				},
				align: 'center',
				text: '收货状态'
			},{
	        dataIndex: 'notes',
	        xytpe: 'linebreakcolumn',
	        sortable: false,	
	        //flex: 2,
	        width:130,
	        align: 'center',
	        text: '备注'
	    },{
			dataIndex: 'isOnline',
			sortable: false,
			//flex: 1,
			width:90,
			renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
				return (value==true)?"在线":"离线";
			},
			align: 'center',
			text: '是否在线'
		},{
				dataIndex: 'isPriceRob',
				sortable: false,
				//flex: 1,
				width:125,
				renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
					return (value==true)?"已开启":"已关闭";
				},
				align: 'center',
				text: '是否自动更新价格'
			},{
			xtype: 'datecolumn',
			format:'Y-m-d H:i:s',
			dataIndex: 'createTime',
			text: '创建时间',
			align: 'center',
			width:140
		},{
			xtype: 'datecolumn',
			format:'Y-m-d H:i:s',
			dataIndex: 'lastUpdateTime',
			align: 'center',
			text: '最后更新时间',
			width:140
		}, {
				dataIndex: 'sevenBaoAccount',
				sortable: false,
				width:140,
				align: 'center',
				text: '7bao账号'
			},{
				dataIndex: 'isAgree',
				sortable: false,
				width:120,
				renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
					return (value==true)?"已经同意":"未同意";
				},
				align: 'center',
				text: '是否同意资金托管'
			},{
				dataIndex: 'isNewFund',
				sortable: false,
				width:140,
				renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
					return (value==true)?"已开通":"未开通";
				},
				align: 'center',
				text: '是否开通新资金流程'
			},{
				dataIndex: 'isCross',
				sortable: false,
				width:140,
				renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
					return (value==true)?"已开通":"未开通";
				},
				align: 'center',
				text: '是否开通合区合服'
			}];
		me.callParent([ cfg ]);
	}
});

//卖家库存信息
Ext.define('MyApp.order.SellerRepositoryPanel',{
	extend: 'Ext.panel.Panel',
	title: '卖家库存信息',
	padding: '5 20 5 20',
	defaults: {
		padding: '5 5 5 5'
	},
	bindData: function(record, grid, rowBodyElement){
		var me = this,
			repositoryGrid = me.getRepositoryGrid(),
			store = repositoryGrid.getStore();
		store.loadPage(1,{
			params: {
				'repository.loginAccount': record.get('loginAccount')
			}
		});
	},
	store: null,
	getStore: function(){
		var me = this;
		if(me.store==null){
			me.store = Ext.create('MyApp.store.RepositoryStore');
		}
		return me.store;
	},
	pagingToolbar: null,
	getPagingToolbar: function(){
		var me = this;
		if(me.pagingToolbar==null){
			me.pagingToolbar = Ext.widget('pagingtoolbar',{
				store: me.getStore(),
				dock: 'bottom',
				displayInfo: true
			});
		}
		return me.pagingToolbar;
	},
	repositoryGrid: null,
	getRepositoryGrid: function(){
		var me = this;
		if(Ext.isEmpty(me.repositoryGrid)){
			me.repositoryGrid = Ext.create('MyApp.view.repositoryGrid',{
				height: 200,
				store: me.getStore(),
				dockedItems: [me.getPagingToolbar()]
			});
		}
		return me.repositoryGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getRepositoryGrid()];
		me.callParent([cfg]);
	}
});

Ext.define('MyApp.view.auditSellerWindow', {
    extend: 'Ext.window.Window',
    width: 450,
    title: '卖家审核',
	closeAction: 'hide',
	modal: true,
	form: null,
	getForm: function(){
		var me = this;
		if(me.form==null){
			me.form = Ext.widget('form',{
                    layout: 'column',
					defaults: {
						margin: '10 10 10 10',
						columnWidth: 1,
						labelWidth: 100
					},
                    items: [{
                    	xtype: 'textfield',
						name: 'loginAccount',
						readOnly: true,
						fieldLabel: '卖家5173账号'
					},{
						xtype: 'textarea',
						name: 'notes',
						height: 100,
						fieldLabel: '理由'
					}],
                    buttons: [{
						text:'保存',
						handler: function() {
							var sellerManager = Ext.getCmp('sellerManager'),
								store = sellerManager.getStore(),
								form = me.getForm(),
								record = form.getRecord();
							record.data.openShState="-1";
							console.log(record.data)
							console.log(me.audit)
							if (form.isValid()) {
								form.updateRecord(record);
								Ext.Ajax.request({
									url : './repository/auditSeller.action',
									jsonData: {
										'seller': record.data,
										'audit': me.audit
									},
									success : function(response, opts) {
										Ext.ux.Toast.msg("温馨提示", "审核成功！");
										store.load();
										me.close();
									},
									exception : function(response, opts) {
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg("温馨提示", json.message);
									}
								});
							}
						}
					}]
                });
		}
		return this.form;
	},
	audit: null,
	bindData: function(record, audit){
		var me = this,
			form = me.getForm().getForm();
		me.audit = audit;
		form.loadRecord(record);
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getForm()]
        });
        me.callParent(arguments);
    }
});

Ext.define('MyApp.view.editSellerWindow', {
	extend: 'Ext.window.Window',
	width: 450,
	title: '修改信息',
	closeAction: 'hide',
	modal: true,
	form: null,
	getForm: function(){
		var me = this;
		if(me.form==null){
			me.form = Ext.widget('form',{
				layout: 'column',
				defaults: {
					margin: '10 10 10 10',
					columnWidth: 1,
					labelWidth: 100
				},
				items: [{
					xtype: 'textfield',
					name: 'loginAccount',
					readOnly: true,
					fieldLabel: '卖家5173账号'
				},{
					xtype: 'textfield',
					name: 'shopName',
					fieldLabel: '店铺名称'
				},{
					xtype: 'textfield',
					name: 'name',
					fieldLabel: '联系人'
				},{
					xtype: 'textfield',
					name: 'phoneNumber',
					fieldLabel: '联系电话'
				},{
					xtype: 'textfield',
					name: 'qq',
					fieldLabel: 'QQ'
				},{
					xtype: 'textfield',
					name: 'games',
					fieldLabel: '关联的游戏'
				},{
					xtype: 'textarea',
					name: 'notes',
					height: 100,
					fieldLabel: '备注'
				}],
				buttons: [{
					text:'保存',
					handler: function() {
						var sellerManager = Ext.getCmp('sellerManager'),
							store = sellerManager.getStore(),
							form = me.getForm(),
							record = form.getRecord();
						if (form.isValid()) {
							form.updateRecord(record);
							Ext.Ajax.request({
								url : './repository/eidtSeller.action',
								jsonData: {
									'updateId': record.data.id,
									'updateLoginAccount':record.get('loginAccount'),
									'updateShopName':record.get('shopName'),
									'updateName':record.get('name'),
									'updatePhoneNumber':record.get('phoneNumber'),
									'updateQQ':record.get('qq'),
									'updateGames':record.get('games'),
									'updateNotes':record.get('notes'),
								},
								success : function(response, opts) {
									Ext.ux.Toast.msg("温馨提示", "保存成功！");
									store.load();
									me.close();
								},
								exception : function(response, opts) {
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg("温馨提示", json.message);
								}
							});
						}
					}
				}]
			});
		}
		return this.form;
	},
	audit: null,
	bindData: function(record, audit){
		var me = this,
			form = me.getForm().getForm();
		me.audit = audit;
		form.loadRecord(record);
	},
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			items: [me.getForm()]
		});
		me.callParent(arguments);
	}
});

Ext.define('MyApp.view.editShopNameWindow', {
	extend: 'Ext.window.Window',
	width: 450,
	title: '修改店铺名称',
	closeAction: 'hide',
	modal: true,
	form: null,
	getForm: function(){
		var me = this;
		if(me.form==null){
			me.form = Ext.widget('form',{
				layout: 'column',
				defaults: {
					margin: '10 10 10 10',
					columnWidth: 1,
					labelWidth: 100
				},
				items: [{
					xtype: 'textfield',
					name: 'loginAccount',
					readOnly: true,
					fieldLabel: '卖家5173账号'
				},{
					xtype: 'textfield',
					name: 'shopName',
					fieldLabel: '店铺名称'
				}],
				buttons: [{
					text:'保存',
					handler: function() {
						var sellerManager = Ext.getCmp('sellerManager'),
							store = sellerManager.getStore(),
							form = me.getForm(),
							record = form.getRecord();
						if (form.isValid()) {
							form.updateRecord(record);
							Ext.Ajax.request({
								url : './repository/eidtSeller.action',
								jsonData: {
									'seller': record.data
								},
								success : function(response, opts) {
									Ext.ux.Toast.msg("温馨提示", "保存成功！");
									store.load();
									me.close();
								},
								exception : function(response, opts) {
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg("温馨提示", json.message);
								}
							});
						}
					}
				}]
			});
		}
		return this.form;
	},
	audit: null,
	bindData: function(record, audit){
		var me = this,
			form = me.getForm().getForm();
		me.audit = audit;
		form.loadRecord(record);
	},
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			items: [me.getForm()]
		});
		me.callParent(arguments);
	}
});

/*
 * 卖家管理页面
 */
Ext.define('MyApp.view.sellerManager', {
    extend: 'Ext.panel.Panel',
    id: 'sellerManager',
    closable: true,
    title: '卖家审核',
    ////////////////////////////////////////////////////
    autoScroll: false,
	listeners:{  
    'resize':function(){
        this.sellerGrid.setHeight(window.document.body.offsetHeight-230);
    	}
	},
	////////////////////////////////////////////////////
    toolbar: null,
	getToolbar: function(){
		var me = this;
		if(Ext.isEmpty(me.toolbar)){
			me.toolbar = Ext.widget('toolbar',{
				dock: 'top',
				items: [{
					text: '审核通过',
					handler: function(){
						me.auditSeller(1);
					}
				},'-',{
					text: '审核不通过',
					handler: function(){
						me.auditSeller(2);
					}
				},'-',{
					text: '赋予权限',
					handler: function(){
						me.givePower();
					}
				},'-',{
					text: '取消权限',
					handler: function(){
						me.cancelPower();
					}
				},'-',{
					text: '寄售',
					handler: function(){
						me.saveShieldSeller();
					}
				},'-',{
					text: '担保',
					handler: function(){
						me.saveManualSeller();
					}
				},'-',{
					text: '小助手',
					handler: function(){
						me.saveHelper();
					}
				},'-',{
					text: '修改',
					handler: function(){
						me.editNote();
					}
				},'-',{
					text: '上线',
					handler: function(){
						me.online();
					}
				},'-',{
					text: '离线',
					handler: function(){
						me.offline();
					}
				}
					,'-',{
						text: '开通收货',
						handler: function(){
							me.open();
						}
					},'-',{
						text: '关闭收货',
						handler: function(){
							me.close();
						}
					}
				]
			});
		}
		return me.toolbar;
	},
	toolbar2:null,
	getToolbar2:function(){
		var me = this;
		if(Ext.isEmpty(me.toolbar2)){
			me.toolbar2 = Ext.widget('toolbar',{
				dock: 'top',
				items: [{
					text: '开启自动更新价格',
					handler: function(){
						me.enablePriceRob();
					}
				},'-',{
					text: '关闭自动更新价格',
					handler: function(){
						me.disablePriceRob();
					}
				}
/*,'-',{
					text: '初始化环信账号',
					handler: function(){
						me.MassProductionAccount();
					}
				}*/,'-',{
					text: '开通新资金流程',
					handler: function(){
						me.openNewFund();
					}
				},'-',{
					text: '关闭新资金流程',
					handler: function(){
						me.closeNewFund();
					}
				},'-',{
						text: '开通合区合服',
						handler: function(){
							me.openCross();
						}
					},'-',{
						text: '关闭合区合服',
						handler: function(){
							me.closeCross();
						}
					}]
			});
		}
		return me.toolbar2;
	},
	window: null,
	editWindow:null,
	getWindow: function(){
		var me = this;
		if(me.window==null){
			me.window = Ext.create('MyApp.view.auditSellerWindow');
		}
		return me.window;
	},
	getEditWindow: function(){
		var me = this;
		if(me.editWindow==null){
			me.editWindow = Ext.create('MyApp.view.editSellerWindow');
		}
		return me.editWindow;
	},
	eidtShopWindow:null,
	getEditShopWindow: function(){
		var me = this;
		if(me.eidtShopWindow==null){
			me.eidtShopWindow = Ext.create('MyApp.view.editShopNameWindow');
		}
		return me.eidtShopWindow;
	},

	auditSeller: function(audit){
		var grid = this.getSellerGrid(),
			window = this.getWindow(),
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示", "请先选择要审核的卖家信息");
			return;
		}
		var record = selRecords[0];
		if(record.get('checkState')==audit){
			if(audit==1){
				Ext.ux.Toast.msg("温馨提示", "该卖家审核状态已为通过");
			}else{
				Ext.ux.Toast.msg("温馨提示", "该卖家审核状态已为不通过");
			}
			return;
		}
		window.bindData(record, audit);
		window.show();
	},
	//赋予短信权限
	givePower:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定赋予该卖家短信权限吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/givePower.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "赋予权限成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	//取消短信权限
	cancelPower:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定取消该卖家短信权限吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/cancelPower.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "取消权限成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	openNewFund:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定开通该卖家的新资金流程吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/openNewFund.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "开通新资金流程成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	closeNewFund:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定关闭该卖家的新资金流程吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/closeNewFund.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "关闭新资金流程成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},

	openCross:function(button,e,eOpts){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		Ext.MessageBox.confirm('温馨提示', '确定开通该卖家的合区合服吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/openCross.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "开通合区合服成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	closeCross:function(button,e,eOpts){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		Ext.MessageBox.confirm('温馨提示', '确定关闭该卖家的合区合服吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/closeCross.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "关闭合区合服成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	//屏蔽卖家
	shieldSeller:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定屏蔽该卖家吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/shieldSeller.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "屏蔽成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	//取消屏蔽
	cancelShield:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定取消该卖家屏蔽吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/cancelShield.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "取消成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	//人工操作库存
	giveManual:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get('isShielded')!=false){
			Ext.ux.Toast.msg("温馨提示","请先解除卖家屏蔽");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定该卖家库存为人工操作吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/giveManualStatus.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	//取消人工操作
	cancelManual:function(button,e,eOpts){
		var grid = this.getSellerGrid();
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
    	Ext.MessageBox.confirm('温馨提示', '确定取消该卖家库存人工操作状态吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/cancelManualStatus.action',
					params:{'id':selRecords[0].get('id')},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "取消成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
    		}else{
    			return;
    		}
		});
	},
	//寄售
	saveShieldSeller:function(button,e,eOpts){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get("isShieldedType")){
			Ext.ux.Toast.msg("温馨提示", "当前已设置为寄售卖家！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定设置为寄售卖家吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/saveShieldSeller.action',
					params:{'id':selRecords[0].get('id'),'isShield':true,'isHelper':false},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	//担保
	saveManualSeller:function(button,e,eOpts){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(!Ext.isEmpty(selRecords[0].get("isShieldedType"))&&!selRecords[0].get("isShieldedType")&&!selRecords[0].get("isHelper")){
			Ext.ux.Toast.msg("温馨提示", "当前已设置为担保卖家！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定设置为担保卖家吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/saveShieldSeller.action',
					params:{'id':selRecords[0].get('id'),'isShield':false,'isHelper':false},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},

	//小助手
	saveHelper:function(button,e,eOpts){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(!Ext.isEmpty(selRecords[0].get("isShieldedType"))&&!selRecords[0].get("isShieldedType")&&selRecords[0].get("isHelper")){
			Ext.ux.Toast.msg("温馨提示", "当前已设置为小助手！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定设置为小助手吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/saveShieldSeller.action',
					params:{'id':selRecords[0].get('id'),'isShield':false,'isHelper':true},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},

	//修改
	editNote: function(){
		var grid = this.getSellerGrid(),
			editWindow = this.getEditWindow(),
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示", "请先选择要修改的卖家信息");
			return;
		}
		var record = selRecords[0];

		editWindow.bindData(record);
		editWindow.show();
	},

	//修改店铺名称
	editShopName: function(){
		var grid = this.getSellerGrid(),
			editWindow = this.getEditShopWindow(),
			selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示", "请先选择要修改的卖家信息");
			return;
		}
		var record = selRecords[0];

		editWindow.bindData(record);
		editWindow.show();
	},

	//上线
	online: function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get("isOnline")){
			Ext.ux.Toast.msg("温馨提示", "当前已设置为上线！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定设置该卖家上线吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/online.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isOnline':true},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	//离线
	offline: function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(!selRecords[0].get("isOnline")){
			Ext.ux.Toast.msg("温馨提示", "当前已设置为离线！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定设置该卖家离线吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/online.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isOnline':false},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	//开通收货
	open: function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get("OpenShState")==1){
			Ext.ux.Toast.msg("温馨提示", "当前卖家已经开通收货系统！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定开通该卖家的收货系统吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/checkSh.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isOpenSh':true,'id':selRecords[0].get("id")},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！！！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	//关闭收货
	close: function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get("OpenShState")==2){
			Ext.ux.Toast.msg("温馨提示", "当前卖家已经关闭收货系统！");
			retrun;
		}
		Ext.MessageBox.confirm('温馨提示', '确定关闭该卖家的收货系统吗？', function(btn){
			console.log(selRecords[0].data);
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/checkSh.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isOpenSh':false},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
	enablePriceRob:function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(selRecords[0].get("isPriceRob")){
			Ext.ux.Toast.msg("温馨提示", "当前已开启自动更新价格！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定开启自动更新价格吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/enablePriceRob.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isPriceRob':true},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},


	MassProductionAccount:function () {
		Ext.Ajax.request({
			url : './repository/MassProductionAccount.action',
			success : function(response, opts) {
				Ext.ux.Toast.msg("温馨提示", "生成成功！");
				grid.getStore().load();
				grid.getSelectionModel().deselectAll();
			},
			exception : function(response, opts) {
				var json = Ext.decode(response.responseText);
				Ext.ux.Toast.msg("温馨提示", json.message);
			}
		});
		  
	},
	
	disablePriceRob:function(){
		var grid = this.getSellerGrid();
		selModel = grid.getSelectionModel(),
			selRecords = selModel.getSelection();
		if(selRecords == null||selRecords.length<=0){
			Ext.ux.Toast.msg("温馨提示","请先选择卖家");
			return;
		}
		if(!selRecords[0].get("isPriceRob")){
			Ext.ux.Toast.msg("温馨提示", "当前已关闭自动更新价格！");
			retrun;
		}

		Ext.MessageBox.confirm('温馨提示', '确定关闭自动更新价格吗？', function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : './repository/enablePriceRob.action',
					params:{'uid':selRecords[0].get("uid"),"loginAccount":selRecords[0].get("loginAccount"),'isPriceRob':false},
					success : function(response, opts) {
						Ext.ux.Toast.msg("温馨提示", "设置成功！");
						grid.getStore().load();
						grid.getSelectionModel().deselectAll();
					},
					exception : function(response, opts) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("温馨提示", json.message);
					}
				});
			}else{
				return;
			}
		});
	},
    queryForm: null,
	getQueryForm: function(){
		var me = this;
		if(me.queryForm==null){
			me.queryForm = Ext.widget('form',{
                layout: 'column',
				defaults: {
					margin: '10 10 10 10',
					columnWidth: .25,
					labelWidth: 80,
					xtype: 'textfield'
				},
                items: [DataDictionary.getDataDictionaryCombo('sellerState',{
                	fieldLabel: '审核状态',
                	labelWidth: 80,
                	columnWidth: .25,
					name: 'checkState',
					editable: false,
					value: 0
				},{value: null,display: '全部'}),{
				    fieldLabel: '卖家5173账号',
				    columnWidth: .25,
				    labelWidth: 100,
				    name: 'loginAccount'
				},{
				    fieldLabel: '联系人',
				    name: 'name'
				},{
				    fieldLabel: '联系电话',
				    name: 'phoneNumber'
				},{
			        fieldLabel: '创建日期',
			        columnWidth: .5,
			        xtype: 'rangedatefield',
			        //起始日期组件的name属性。
			        fromName: 'createStartTime',
			        //终止日期组件的name属性。
			        toName: 'createEndTime'
			    },{
					fieldLabel: '审核客服',
					hidden: CurrentUser.getUserTypeCode()!=2&&CurrentUser.getUserTypeCode()!=3,
				    name: 'auditServiceAccount'
				}],
                buttons: [{
					text:'重置',
					handler: function() {
						me.getQueryForm().getForm().reset();
					}
				},'->',{
					text:'查询',
					handler: function() {
						me.getPagingToolbar().moveFirst();
					}
				}]
            });
		}
		return this.queryForm;
	},
	store: null,
	getStore: function(){
		var me = this;
		if(me.store==null){
			me.store = Ext.create('MyApp.store.SellerStore',{
				autoLoad: true,
				listeners: {
					beforeload : function(store, operation, eOpts) {
						var queryForm = me.getQueryForm();
						if (queryForm != null) {
							var values = queryForm.getValues();
							Ext.apply(operation, {
								params: {
									'auditServiceAccount': Ext.String.trim(values.auditServiceAccount),
									'seller.loginAccount': Ext.String.trim(values.loginAccount),
									'seller.name': Ext.String.trim(values.name),
									'seller.checkState': values.checkState,
									'seller.phoneNumber': Ext.String.trim(values.phoneNumber),
									'createStartTime': values.createStartTime,
									'createEndTime': values.createEndTime,
									'seller.messagePower': values.messagePower
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
	getPagingToolbar: function(){
		var me = this;
		if(me.pagingToolbar==null){
			me.pagingToolbar = Ext.widget('pagingtoolbar',{
				store: me.getStore(),
				dock: 'bottom',
				displayInfo: true
			});
		}
		return me.pagingToolbar;
	},
	sellerGrid: null,
	getSellerGrid: function(){
		var me = this;
		if(Ext.isEmpty(me.sellerGrid)){
			me.sellerGrid = Ext.create('MyApp.view.sellerGrid',{
				store: me.getStore(),
				dockedItems: [me.getToolbar(),me.getToolbar2(),me.getPagingToolbar()],
				plugins: [{
                    ptype: 'gamegoldrowexpander',
                    header: true,
                    rowsExpander: false,
                    rowBodyElement : 'MyApp.order.SellerRepositoryPanel'
                }]
			});
		}
		return me.sellerGrid;
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getQueryForm(),me.getSellerGrid()]
        });
        me.callParent(arguments);
    }
});