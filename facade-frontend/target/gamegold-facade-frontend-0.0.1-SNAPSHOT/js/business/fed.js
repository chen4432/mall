(function (a) {
    a.fed = a.fed || {};
    a.extend(a.fed, {
        footer: function () {
            a("#footer1000").length > 0 ? a("#footer1000").html("<ul class='footer_nav'>\t<li><a href='http://www.5173.com/About' target='_blank' rel='nofollow'>\u5173\u4e8e\u6211\u4eec</a>|</li>\t<li><a href='http://www.5173.com/About/Partners' target='_blank' rel='nofollow'>\u5408\u4f5c\u4f19\u4f34</a>|</li>\t<li><a href='http://www.5173.com/About/Cooperation' target='_blank' rel='nofollow'>\u5408\u4f5c\u8054\u7cfb</a>|</li>\t<li><a href='http://www.5173.com/About/Protection' target='_blank' rel='nofollow'>\u9690\u79c1\u4fdd\u62a4</a>|</li>\t<li><a href='http://sc.5173.com/' target='_blank' rel='nofollow'>\u6295\u8bc9\u5efa\u8bae</a>|</li>\t<li><a href='http://www.5173.com/About/Copyright' target='_blank' rel='nofollow'>\u514d\u8d23\u58f0\u660e</a>|</li>\t<li><a href='http://www.5173.com/about/job' target='_blank' rel='nofollow'>\u8bda\u8058\u82f1\u624d</a></li></ul><p class='copyright'>Copyright \u00a9 2002-2015 5173.com \u7248\u6743\u6240\u6709<a href='http://www.miitbeian.gov.cn/' target='_blank' rel='nofollow'>ICP\u8bc1\uff1a\u6d59B2-20090127 \uff08\u91d1\u534e\u6bd4\u5947\u7f51\u7edc\u6280\u672f\u6709\u9650\u516c\u53f8\uff09</a><a href='http://images001.5173cdn.com/html/license/license.html' target='_blank' rel='nofollow'>\u3010\u7f51\u7edc\u6587\u5316\u7ecf\u8425\u8bb8\u53ef\u8bc1\u3011\u6d59\u7f51\u6587[2013]0619-067\u53f7</a></p><ul class='honor_list'>\t<li class='item1'><a href='http://help.5173.com/winner100/winner100.html' target='_blank' rel='nofollow'>\u4e9a\u6d32\u4e2d\u5c0f\u79c1\u8425\u4f01\u4e1a100\u5f3a</a></li>\t<li class='item2'><a href='http://www.ectrustprc.org.cn/seal/splash/2000003.htm'  target='_blank' rel='nofollow'>\u4e2d\u56fd\u7535\u5b50\u5546\u52a1\u8bda\u4fe1\u5355\u4f4d</a></li>\t<li class='item4'><a href='http://help.5173.com/cxlm/01.html' target='_blank' rel='nofollow'>\u53cd\u76d7\u53f7\u7eff\u8272\u8054\u76df</a></li>   <li class='item8'><a href='https://search.szfw.org/cert/l/CX20141230006228006321' target='_blank' rel='nofollow'>\u8bda\u4fe1\u7f51\u7ad9</a></li>   <li class='item10'><a href='http://www.jhgs.gov.cn/zmhdpd/' target='_blank' rel='nofollow'>\u91d1\u534e\u7f51\u4e0a\u5e02\u573a\u76d1\u7ba1\u5c40</a></li></ul><ul class='honor_list honor_list_2' style='padding-left:234px;'>\t<li class='item6'><a href='http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action'  target='_blank' rel='nofollow'>\u5de5\u4fe1\u90e8\u57df\u540d\u5907\u6848\u7ba1\u7406\u7cfb\u7edf </a></li>\t<li class='item3'><a href='http://www.315online.com.cn/member/315090077.html' target='_blank' rel='nofollow'>\u7f51\u4e0a\u4ea4\u6613\u4fdd\u969c\u4e2d\u5fc3</a></li>\t<li class='item7'><a href='http://www.idinfo.cn/SignHandle?userID=3307030000019094' target='_blank' rel='nofollow'>\u4f01\u4e1a\u6cd5\u4eba\u8425\u4e1a\u6267\u7167</a></li>\t<li class='item9'><a href='http://www.51315.cn/' target='_blank' rel='nofollow'>\u8bda\u4fe1\u5728\u7ebf</a></li></ul>") : a("body").append("<div></div>").append(a("<div class='footer'></div>").html("<ul class='footer_nav'>\t<li><a href='http://www.5173.com/About' target='_blank' rel='nofollow'>\u5173\u4e8e\u6211\u4eec</a>|</li>\t<li><a href='http://www.5173.com/About/Partners' target='_blank' rel='nofollow'>\u5408\u4f5c\u4f19\u4f34</a>|</li>\t<li><a href='http://www.5173.com/About/Cooperation' target='_blank' rel='nofollow'>\u5408\u4f5c\u8054\u7cfb</a>|</li>\t<li><a href='http://www.5173.com/About/Protection' target='_blank' rel='nofollow'>\u9690\u79c1\u4fdd\u62a4</a>|</li>\t<li><a href='http://sc.5173.com/' target='_blank' rel='nofollow'>\u6295\u8bc9\u5efa\u8bae</a>|</li>\t<li><a href='http://www.5173.com/About/Copyright' target='_blank' rel='nofollow'>\u514d\u8d23\u58f0\u660e</a>|</li>\t<li><a href='http://www.5173.com/about/job' target='_blank' rel='nofollow'>\u8bda\u8058\u82f1\u624d</a></li></ul><p class='copyright'>Copyright \u00a9 2002-2015 5173.com \u7248\u6743\u6240\u6709<a href='http://www.miitbeian.gov.cn/' target='_blank' rel='nofollow'>ICP\u8bc1\uff1a\u6d59B2-20090127 \uff08\u91d1\u534e\u6bd4\u5947\u7f51\u7edc\u6280\u672f\u6709\u9650\u516c\u53f8\uff09</a><a href='http://images001.5173cdn.com/html/license/license.html' target='_blank' rel='nofollow'>\u3010\u7f51\u7edc\u6587\u5316\u7ecf\u8425\u8bb8\u53ef\u8bc1\u3011\u6d59\u7f51\u6587[2013]0619-067\u53f7</a></p><ul class='honor_list'>\t<li class='item1'><a href='http://help.5173.com/winner100/winner100.html' target='_blank' rel='nofollow'>\u4e9a\u6d32\u4e2d\u5c0f\u79c1\u8425\u4f01\u4e1a100\u5f3a</a></li>\t<li class='item2'><a href='http://www.ectrustprc.org.cn/seal/splash/2000003.htm'  target='_blank' rel='nofollow'>\u4e2d\u56fd\u7535\u5b50\u5546\u52a1\u8bda\u4fe1\u5355\u4f4d</a></li>\t<li class='item4'><a href='http://help.5173.com/cxlm/01.html' target='_blank' rel='nofollow'>\u53cd\u76d7\u53f7\u7eff\u8272\u8054\u76df</a></li>   <li class='item8'><a href='https://search.szfw.org/cert/l/CX20141230006228006321' target='_blank' rel='nofollow'>\u8bda\u4fe1\u7f51\u7ad9</a></li>   <li class='item10'><a href='http://www.jhgs.gov.cn/zmhdpd/' target='_blank' rel='nofollow'>\u91d1\u534e\u7f51\u4e0a\u5e02\u573a\u76d1\u7ba1\u5c40</a></li></ul><ul class='honor_list honor_list_2' style='padding-left:234px;'>\t<li class='item6'><a href='http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action'  target='_blank' rel='nofollow'>\u5de5\u4fe1\u90e8\u57df\u540d\u5907\u6848\u7ba1\u7406\u7cfb\u7edf </a></li>\t<li class='item3'><a href='http://www.315online.com.cn/member/315090077.html' target='_blank' rel='nofollow'>\u7f51\u4e0a\u4ea4\u6613\u4fdd\u969c\u4e2d\u5fc3</a></li>\t<li class='item7'><a href='http://www.idinfo.cn/SignHandle?userID=3307030000019094' target='_blank' rel='nofollow'>\u4f01\u4e1a\u6cd5\u4eba\u8425\u4e1a\u6267\u7167</a></li>\t<li class='item9'><a href='http://www.51315.cn/' target='_blank' rel='nofollow'>\u8bda\u4fe1\u5728\u7ebf</a></li></ul>"))
        }
    })
})(jQuery);

$(function () {
    try {
        FED_AUTOFOOTER && $.fed.footer()
    } catch (a) {
        $.fed.footer()
    }
    window.status = "\u6211\u4eec\u53d1\u6398\u4e86\u865a\u62df\u7269\u54c1\u4ea4\u6613\u884c\u4e1a\uff0c\u5e76\u4e00\u76f4\u5f15\u9886\u5b83\u524d\u8fdb\uff01\uff01"
});

var top_v3_json = {
    buyers: {
        name: "\u4e70\u5bb6\u4e2d\u5fc3",
        href: "http://user.5173.com/default.aspx",
        "class": "",
        target: "_self",
        item: [{
            name: "\u4e70\u5bb6\u4e2a\u4eba\u4e2d\u5fc3",
            href: "http://user.5173.com/default.aspx",
            target: "_self",
            "class": ""
        }, {
            name: "\u6211\u8d2d\u4e70\u7684\u5546\u54c1",
            href: "http://trading.5173.com/list/viewmyorderlist.aspx",
            target: "_self",
            "class": ""
        }, {
            name: "\u6211\u7684\u4ee3\u7ec3\u8ba2\u5355",
            href: "http://dlc2c.5173.com/main/dlplayorderlist.aspx?ishistory=0",
            target: "_self",
            "class": "current"
        }, {
            name: "\u6211\u8981\u4e70",
            href: "http://trading.5173.com/",
            target: "_blank",
            "class": ""
        }, {
            name: "\u5145\u503c",
            href: "https://mypay.ebatong.com/charge/chargeebank.aspx?flag=1",
            target: "_blank",
            "class": ""
        }]
    },
    seller: {
        name: "\u5356\u5bb6\u4e2d\u5fc3",
        href: "http://user.5173.com/default.aspx",
        "class": "",
        target: "_self",
        item: [{
            "class": "",
            name: "\u5356\u5bb6\u4e2a\u4eba\u4e2d\u5fc3",
            href: "http://user.5173.com/default.aspx",
            target: "_self"
        }, {
            "class": "",
            name: "\u6211\u7684\u8ba2\u5355\u7ba1\u7406",
            href: "http://trading.5173.com/list/myorder_manage.aspx",
            target: "_self"
        }, {
            "class": "",
            name: "\u51fa\u552e\u4e2d\u7684\u5546\u54c1",
            href: "http://trading.5173.com/list/viewmybizofferslist2.aspx",
            target: "_self"
        }, {
            "class": "",
            name: "\u4ed3\u5e93\u4e2d\u7684\u5546\u54c1",
            href: "http://trading.5173.com/list/viewpausedbizofferslist.aspx",
            target: "_self"
        }, {
            "class": "",
            name: "\u6700\u65b0\u6210\u4ea4\u4ef7\u683c",
            href: "http://trading.5173.com/list/ViewLastestDealList.aspx",
            target: "_self"
        }, {
            "class": "",
            name: "\u6211\u7684\u4ee3\u7ec3\u8ba2\u5355",
            href: "http://dlc2c.5173.com/main/neworderlist.aspx?ishistory=0",
            target: "_self"
        }, {
            "class": "",
            name: "\u6211\u8981\u5356",
            href: "http://trading.5173.com/auction/newpublish.aspx",
            target: "_blank"
        }, {
            "class": "",
            name: "\u63d0\u73b0",
            href: "https://mypay.ebatong.com/charge/userwithdrawal.aspx",
            target: "_blank"
        }]
    },
    recharge: {
        name: "\u5145\u503c",
        href: "https://mypay.ebatong.com/charge/chargeebank.aspx?flag=1",
        "class": "",
        target: "_self"
    },
    withdrawals: {
        name: "\u63d0\u73b0",
        href: "https://mypay.ebatong.com/charge/userwithdrawal.aspx",
        "class": "",
        target: "_self"
    },
    client: {
        name: "\u5ba2\u670d\u4e2d\u5fc3",
        href: "http://kf.5173.com",
        "class": "",
        target: "_blank",
        item: [{
            name: "\u54a8\u8be2\u4e2d\u5fc3",
            "class": "",
            href: "http://sc.5173.com/",
            target: "_blank"
        }, {
            name: "\u5b89\u5168\u4e2d\u5fc3",
            "class": "",
            href: "http://safe.5173.com/",
            target: "_blank"
        }, {
            name: "\u5e2e\u52a9\u4e2d\u5fc3",
            "class": "",
            href: "http://aid.5173.com/",
            target: "_blank"
        }, {
            name: "\u6211\u8981\u54a8\u8be2",
            "class": "current",
            href: "http://sc.5173.com/index.php?question/ask/",
            target: "_blank"
        }, {
            name: "\u6211\u8981\u6295\u8bc9",
            "class": "current",
            href: "http://sc.5173.com/?question/complain.html",
            target: "_blank"
        }]
    },
    navigation: {
        name: "\u7f51\u7ad9\u5bfc\u822a",
        href: "http://user.5173.com/default.aspx",
        "class": "",
        item: [{
            name: "\u5546\u54c1\u7c7b",
            "class": "",
            itemlist: [{
                name: "\u91d1\u5e01\u4ea4\u6613",
                "class": "",
                href: "http://yxb.5173.com",
                target: "_blank"
            }, {
                name: "\u5e10\u53f7\u4ea4\u6613",
                "class": "",
                href: "http://gameid.5173.com",
                target: "_blank"
            }, {
                name: "\u88c5\u5907\u4ea4\u6613",
                "class": "",
                href: "http://zb.5173.com/",
                target: "_blank"
            }, {
                name: "\u6e38\u620f\u4ee3\u7ec3",
                "class": "",
                href: "http://dlc2c.5173.com/",
                target: "_blank"
            }, {
                name: "\u70b9\u5361\u4ea4\u6613",
                "class": "",
                href: "http://dkjy.5173.com/",
                target: "_blank"
            }, {
                name: "\u5e10\u53f7\u79df\u8d41",
                "class": "",
                href: "http://tool.5173.com/AccountRent/Index.aspx#",
                target: "_blank"
            }, {
                name: "\u68cb\u724c\u6e38\u620f",
                "class": "",
                href: "http://zzjy.5173.com/",
                target: "_blank"
            }]
        }, {
            name: "\u670d\u52a1\u7c7b",
            "class": "",
            itemlist: [{
                name: "\u8d44\u6599\u4fee\u6539",
                "class": "",
                href: "http://gameid.5173.com/fworder/publish.aspx?u_ref=gameid_index",
                target: "_blank"
            }, {
                name: "\u7f51\u6e38\u52a9\u624b",
                "class": "",
                href: "http://tool.5173.com",
                target: "_blank"
            }, {
                name: "\u6295\u8bc9\u5efa\u8bae",
                "class": "",
                href: "http://bar.5173.com",
                target: "_blank"
            }, {
                name: "\u54a8\u8be2\u4e2d\u5fc3",
                "class": "",
                href: "http://sc.5173.com",
                target: "_blank"
            }, {
                name: "\u5b89\u5168\u4e2d\u5fc3",
                "class": "",
                href: "http://safe.5173.com",
                target: "_blank"
            }, {
                name: "5173\u52a9\u7406",
                "class": "",
                href: "http://redtrading.5173.com",
                target: "_blank"
            }, {
                name: "\u77ed\u4fe1\u670d\u52a1",
                "class": "",
                href: "http://mobile.5173.com/Subscription/Index",
                target: "_blank"
            }, {
                name: "\u63a8\u5e7f\u8054\u76df",
                "class": "",
                href: "http://cps.5173.com/",
                target: "_blank"
            }]
        }, {
            name: "\u5176\u4ed6",
            "class": "",
            itemlist: [{
                name: "\u624b\u673a5173",
                "class": "",
                href: "http://app.5173.com/",
                target: "_blank"
            }, {
                name: "\u6e38\u620f\u8d44\u8baf",
                "class": "",
                href: "http://html.5173.com/operation/2012/10/26news.html",
                target: "_blank"
            }, {
                name: "\u9875\u6e38\u5f00\u670d",
                "class": "",
                href: "http://kaifu.5173.com/",
                target: "_blank"
            }]
        }]
    },
    microblog: {
        name: "\u5b98\u65b9\u5fae\u535a",
        href: "http://user.5173.com/default.aspx",
        "class": "",
        item: {
            xlwb: {
                name: "\u65b0\u6d6a\u5fae\u535a",
                href: "http://e.weibo.com/1804287704/profile",
                target: "_blank",
                "class": ""
            },
            txwb: {
                name: "\u817e\u8baf\u5fae\u535a",
                href: "http://e.t.qq.com/good5173",
                target: "_blank",
                "class": ""
            },
            wx: {
                name: "\u5173\u6ce85173\u5fae\u4fe1",
                href: "",
                target: "_blank",
                "class": ""
            }
        }
    }
};

(function () {
    var a = {
        init: function () {
            if ($("#J_GlobalTop")[0]) {
                this.renderHtml();
                this.login()
            }
        },
        easyAjax: function (g, d, c, b) {
            g = {
                url: g,
                dataType: "jsonp",
                scriptCharset: "gb2312",
                jsonp: "jsoncallback",
                success: c,
                complete: b
            };
            if (d) {
                g.cache = true;
                g.jsonpCallback = d
            }
            $.ajax(g)
        },
        renderHtml: function () {
            if (window.location.href.indexOf("5173.com") != "-1") this.url = window.location.href || "http://www.5173.com";
            if (typeof top_v3_json !== "undefined") {
                var g = top_v3_json,
                    d = "",
                    c = "",
                    b = "",
                    e = "";
                try {
                    $.each(g.buyers.item, function (j, l) {
                        d += '<li class="' + l["class"] + '"><a target="' + l.target + '"  href="' + l.href + '" ref="nofollow">' + l.name + "</a></li>"
                    });
                    $.each(g.seller.item, function (j, l) {
                        c += '<li class="' + l["class"] + '"><a target="' + l.target + '"  href="' + l.href + '" ref="nofollow">' + l.name + "</a></li>"
                    });
                    $.each(g.client.item, function (j, l) {
                        b += '<li class="' + l["class"] + '"><a target="' + l.target + '"  href="' + l.href + '" ref="nofollow">' + l.name + "</a></li>"
                    });
                    $.each(g.navigation.item, function (j, l) {
                        var k = "";
                        $.each(l.itemlist, function (n, p) {
                            k += '<dd><a href="' + p.href + '" target="' + p.target + '" ref="nofollow">' + p.name + "</a></dd>"
                        });
                        e += '  <dl class="map_list">                            <dt>' + l.name + "</dt>" + k + "                        </dl>"
                    });
                    var f = '<div class="header header_index950">    <div class="top">        <div class="top_box clearfix">            <ul class="top_left" id="loginList">                <li>\u60a8\u597d\uff01</li>                <li>\u8bf7<a target="_self" href="https://passport.5173.com/?returnUrl=' + escape(this.url) + '">\u767b\u5f55</a></li>                <li><a title="QQ\u767b\u5f55" class="qq_login f_666" href="https://passport.5173.com/Partner/LoginFrom?appNo=qq&amp;returnUrl=' + escape(this.url) + '">QQ\u767b\u5f55</a></li>                <li><a target="_blank" href="https://passport.5173.com/User/Register">\u514d\u8d39\u6ce8\u518c</a></li>            </ul>            <ul id="topRight" class="top_right">                <li class="' + g.buyers["class"] + '">                    <a target="' + g.buyers.target + '" href="' + g.buyers.href + '" ref="nofollow">' + g.buyers.name + '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="user_list">' + d + '                        </ul>                    </div>                </li>                <li class="' + g.seller["class"] + '">                    <a target="' + g.seller.target + '" href="' + g.seller.href + '" ref="nofollow">' + g.seller.name + '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="user_list">' + c + '                        </ul>                    </div>                </li>                <li class="pay_chognzhi" id="payChognzhi">                    <a href="' + g.recharge.href + '" target="' + g.recharge.target + '" ref="nofollow">' + g.recharge.name + '<em class="line">|</em> </a>                </li>                <li class="pay_tixian" id="payTixian">                    <a href="' + g.withdrawals.href + '" target="' + g.withdrawals.target + '" ref="nofollow">' + g.withdrawals.name + '<em class="line">|</em> </a>                </li>                <li id="markCart" class="mark_cart">                    <a href="http://trading.5173.com/list/viewmyshoppingcart.aspx" ref="nofollow"><s class="icon_cart"></s>\u8d2d\u7269\u8f66&nbsp;<b class="num">0</b>&nbsp;\u4ef6<em class="line">|</em></a>                </li>                <li class="' + g.client["class"] + '">                    <a target="' + g.client.target + '" href="' + g.client.href + '" ref="nofollow">' + g.client.name + '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="support_list">' + b + '                        </ul>                    </div>                </li>                <li class="' + g.navigation["class"] + '"><span>' + g.navigation.name + '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></span>                    <div class="sub_box map_box">' + e + '                    </div>                </li>                                          </ul>        </div>    </div></div>'
                } catch (h) {
                    typeof window.console !== "undefined" && console.log("\u516c\u5171\u9876\u90e8\u901a\u680f\u62a5\u9519 ===> " + h.message)
                }
                $("#J_GlobalTop").append(f);
                this.dropDownEvt()
            }
        },
        dropDownEvt: function () {
            if ($("#topRight .black_arrow").css("transition")) var g = false;
            $("#topRight > li").bind("mouseenter", function () {
                var d = $(this).find("div.sub_box");
                if (d.length) {
                    $(this).css("zIndex", "1002").addClass("current");
                    g || $(this).find(".black_arrow").addClass("arrow_up");
                    d.show()
                }
            }).bind("mouseleave", function () {
                var d = $(this).find("div.sub_box");
                if (d.length) {
                    $(this).css("zIndex", "").removeClass("current");
                    g || $(this).find(".black_arrow").removeClass("arrow_up");
                    d.hide()
                }
            })
        },
        initMsg: function () {
            this.easyAjax("http://message.5173.com/Service/GetUserSiteMsg.aspx", null, function (g) {
                if (g) {
                    var d = g.length,
                        c = $("#msgNum"),
                        b = $("#msgList"),
                        e = '<div class="switchable_container"><ul>',
                        f;
                    if (d > 0) {
                        $.each(g, function (h, j) {
                            e += "<li><h3>" + j.title + '</h3><p class="msg_desc"><a href="' + j.linkUrl + '">' + j.msg + '</a></p><p><a href="' + j.linkUrl + '" class="btn">' + j.linkText + "</a></p></li>"
                        });
                        e += '</ul></div><div class="msg_bottom"><a href="http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0" class="more">\u67e5\u770b\u5168\u90e8</a><span id="msgPageIndex"><em class="current">1</em>/<em class="length">' + d + '</em></span><a href="#" class="switchable_prev">\u4e0a\u4e00\u6761</a><a href="#" class="switchable_next">\u4e0b\u4e00\u6761</a></div>';
                        f = $("#msgBox").html(e);
                        c.addClass("highlight");
                        b.hover(function () {
                            $(this).css("zIndex", "1002").addClass("current");
                            f.show()
                        }, function () {
                            $(this).css("zIndex", "").removeClass("current");
                            f.hide()
                        });
                        f.switchable({
                            effects: "vertical",
                            nav: false,
                            index: false,
                            auto: false,
                            duration: 400,
                            callback: function (h) {
                                $("#msgPageIndex .current").text(h + 1)
                            }
                        })
                    }
                }
            })
        },
        login: function () {
            var g = this;
            g.easyAjax("http://user.5173.com/Ajax/FrameLoginHandle.ashx", null, function (d) {
                if (typeof d.name !== "undefined" && d.name) {
                    var c = '<li><b class="f_333">' + d.name + '</b></li><li id="msgList"><a href="http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0" class="msg">\u7ad9\u5185\u4fe1<span id="msgNum">' + d.msg + '</span><em class="line">|</em></a><div class="msg_box" id="msgBox"></div></li><li><a href="#" class="login_out" id="loginOut">\u9000\u51fa</a></li>';
                    $("#loginList").html(c);
                    d.msg !== "0" && $("#msgNum").addClass("highlight");
                    g.easyAjax("http://fcd.5173.com/shoppingcart/getcount.aspx", null, function (e) {
                        $("#markCart b.num").text(e.count)
                    });
                    $("#loginOut").click(function (e) {
                        var f = window.location.href || "http://www.5173.com",
                            h;
                        try {
                            if (KeyUndecode) h = "&Undecode=" + KeyUndecode
                        } catch (j) {
                            h = ""
                        }
                        window.location.href = "http://passport.5173.com/Sso/Logout?returnUrl=" + escape(f) + h;
                        e.preventDefault()
                    });
                    window.isSignIn = true
                } else window.isSignIn = false;
                window.loginData = d;
                if (typeof window.loginCallback === "function") {
                    window.loginCallback(d);
                    try {
                        window.loginData = window.loginCallback = null;
                        delete window.loginCallback;
                        delete window.loginData
                    } catch (b) { }
                }
            })
        },
        bfd: function () { }
    };
    $(function () {
        a.init()
    })
})();

$(function () {
    var a = {
        init: function () {
            var g = this;
            g.entry()
        },
        easyAjax: function (g) {
            var d = {
                type: "GET",
                url: "null",
                scriptCharset: "utf-8",
                dataType: "jsonp",
                jsonp: "jsoncallback",
                jsonpCallback: "null",
                data: {},
                cache: false,
                success: function () { },
                error: function (c, b, e) {
                    if (navigator.userAgent.indexOf("MSIE") < 0) {
                        console.log(c);
                        console.log(b);
                        console.log(e)
                    }
                }
            };
            $.extend(d, g);
            return $.ajax(d)
        },
        entry: function () {
            var g = this,
                d = "";
            entryFun = function (c) {
                d = "";
                $.each(c, function (b, e) {
                    if (b < 8) d += e.Text == "5173\u7f8e\u5973\u76f4\u64ad" ? '<li><a href="' + e.Url + "\" onclick=\"__utmTrackEvent(encodeURIComponent('\u65b0\u7248\u9996\u9875'),encodeURIComponent('\u70ed\u95e8\u6e38\u620f'),encodeURIComponent('" + e.Text + '\'));" style="color:#f60">' + e.Text + "</a></li>" : '<li><a href="' + e.Url + "\" onclick=\"__utmTrackEvent(encodeURIComponent('\u65b0\u7248\u9996\u9875'),encodeURIComponent('\u70ed\u95e8\u6e38\u620f'),encodeURIComponent('" + e.Text + "'));\">" + e.Text + "</a></li>"
                });
                $(".hot_game_list").empty().append(d)
            };
            $(".hot_game_list").empty().append("\u6b63\u5728\u52a0\u8f7d\u4e2d\u3002\u3002\u3002");
            g.easyAjax({
                url: "http://fcd.5173.com/CommonData/GetDataForWww.aspx?type=hotgame",
                jsonpCallback: "quick",
                success: entryFun
            })
        }
    };
    window.quick = a;
    a.init()
});