var MaxTable = function () {
    this.page = new Pages();
    this.defaultSortInfo = {field: '', fieldName: '', sortOrder: ''};
    this.sortInfo = this.defaultSortInfo;
    this.showPageInfo = true;
    this.table = null
};
MaxTable.prototype = {
    initialize: function (opts) {
        this.opts = $.extend({id: 'id'}, opts || {});
        this._s = $("#" + this.opts.table)[0];
        if (this.opts.isSort == false) {
            this.sortInfo = this.defaultSortInfo
        }
        this.showPageInfo = (this.opts.showPageInfo == undefined) ? true : this.opts.showPageInfo;
        this.complete = this.opts.complete;
        this.beforeSend = this.opts.beforeSend;
        this.isChecked = this.opts.isChecked;
        this.table = this.opts.table
    }, onLoad: function (parameters) {
        this.searchParameters = parameters;
        var opts = this.opts;
        var _s = this._s;
        var url = opts.queryUrl;
        var _parent = this;
        $.ajax({
            type: "POST",
            url: opts.queryUrl,
            data: this.getQueryData(parameters),
            dataType: "json",
            beforeSend: function (XHR) {
                if (_parent.beforeSend) {
                    _parent.beforeSend()
                }
            },
            success: function (datas) {
                try {
                    var data = datas.data;
                    _parent.page = datas.page;
                    while (_s.rows.length > 0) {
                        _s.deleteRow(0)
                    }
                    if (data.length == 0) {
                        var trObj = _s.insertRow(0);
                        trObj.id = "page";
                        var tdObj = trObj.insertCell(0);
                        tdObj.colSpan = opts.headerColumns.length;
                        tdObj.innerHTML = "没有记录！"
                    } else {
                        for (var i = 0; i < data.length; i += 1) {
                            var trObj = _s.insertRow(_s.rows.length);
                            trObj.id = i + 1;
                            if (i % 2 == 0) {
                                trObj.className = "td1"
                            } else {
                                trObj.className = "td2"
                            }
                            for (var j = 0; j < opts.headerColumns.length; j += 1) {
                                var tdObj = trObj.insertCell(j);
                                tdObj.className = "center";
                                if (opts.headerColumns[j].renderer) {
                                    tdObj.innerHTML = opts.headerColumns[j].renderer(data[i][opts.id], data[i][opts.headerColumns[j].id], data[i], trObj.id, _parent.isChecked)
                                } else {
                                    tdObj.innerHTML = data[i][opts.headerColumns[j].id]
                                }
                            }
                        }
                        if (_parent.showPageInfo) {
                            if (_parent.page.totalCount != 0) {
                                var trObj = _s.insertRow(_s.rows.length);
                                trObj.id = "page";
                                var tdObj = trObj.insertCell(0);
                                tdObj.colSpan = opts.headerColumns.length;
                                tdObj.style = "text-align:right";
                                tdObj.innerHTML = _parent.createPage(_parent.page);
                                $("#pageSize", tdObj).bind("blur", function () {
                                    var p1 = /^\d{1,}$/;
                                    if (!p1.test(this.value)) {
                                        alert("分页只能是数字！");
                                        return
                                    }
                                    if (parseInt(this.value) <= 0) {
                                        alert("分页数字不正确！");
                                        return
                                    }
                                    _parent.page.pageSize = this.value;
                                    _parent.page.totalCount = -1;
                                    _parent.page.pageNo = 1;
                                    _parent.query()
                                });
                                $("#firstPage", tdObj).bind("click", function () {
                                    _parent.page.pageNo = 1;
                                    _parent.query()
                                });
                                $("#prePage", tdObj).bind("click", function () {
                                    _parent.page.pageNo = _parent.page.pageNo - 1;
                                    _parent.query()
                                });
                                $("#nextPage", tdObj).bind("click", function () {
                                    _parent.page.pageNo = _parent.page.pageNo + 1;
                                    _parent.query()
                                });
                                $("#lastPage", tdObj).bind("click", function () {
                                    _parent.page.pageNo = _parent.page.totalPage;
                                    _parent.query()
                                });
                                $("#Pg", tdObj).bind("blur", function () {
                                    var p1 = /^\d{1,}$/;
                                    if (!p1.test(this.value)) {
                                        alert("分页只能是数字！");
                                        return
                                    }
                                    if (parseInt(this.value) <= 0) {
                                        alert("分页数字不正确！");
                                        return
                                    }
                                    _parent.page.pageNo = this.value;
                                    _parent.query()
                                })
                            }
                        }
                    }
                    if (_parent.complete) {
                        _parent.complete(_parent)
                    }
                } catch (e) {
                    console.log(e)
                }
                if (_parent.complete) {
                    $("#" + _parent.table + " tr").mouseover(function () {
                        if ($(this).attr("id") != "page") {
                            $(this).addClass("over")
                        }
                    }).mouseout(function () {
                        if ($(this).attr("id") != "page") {
                            $(this).removeClass("over")
                        }
                    });
                }
            },
            error: function (response) {
                if (response != null && response != undefined && response.responseText.length > 0) {
                    if (response.responseText.indexOf('loginwindow') != -1) {
                        parent.location.reload()
                    } else {
                        alert(response.responseText)
                    }
                }
            }
        });
        var temp = $('input[name="c_all"]')[0] + "";
        $('input[name="c_all"]').attr("checked", false);
        if (temp != 'undefined') {
            $('input[name="c_all"]')[0].nextSibling.nodeValue = "全选"
        }
    }, createPage: function (pageInfo) {
        var text = '';
        text += '每页显示<input id="pageSize"   value="' + pageInfo.pageSize + '" type="text" id="pageSize" class="page_ipt" />';
        text += '条&nbsp;|&nbsp;共<font>' + pageInfo.totalPage + '</font>页 , <font>' + pageInfo.totalCount + '</font>条数据';
        text += '&nbsp;|&nbsp;';
        text += '<input id="firstPage"  type="button" ' + (pageInfo.pageNo > 1 ? 'style="background:#FFF ; border:none;cursor:pointer"  ' : ' disabled="disabled" ') + '   name="button" id="button" value="首页" class="page_btn" />&nbsp;';
        text += '<input id="prePage"  type="button" ' + (pageInfo.pageNo > 1 ? '  style="background:#FFF ; border:none;cursor:pointer" ' : ' disabled="disabled" ') + '   name="button" id="button" value="上一页" class="page_btn" "/>&nbsp;';
        text += '<input id="nextPage"  type="button" ' + (pageInfo.totalPage > pageInfo.pageNo ? ' style="background:#FFF ; border:none;cursor:pointer"  ' : ' disabled="disabled" ') + '   name="button" id="button" value="下一页" class="page_btn" />&nbsp;';
        text += '<input id="lastPage"  type="button"  ' + (pageInfo.totalPage > pageInfo.pageNo ? ' style="background:#FFF ; border:none;cursor:pointer"  ' : ' disabled="disabled" ') + '  name="button" id="button" value="尾页" class="page_btn" />&nbsp;';
        text += '&nbsp;|&nbsp;第<input id="Pg"   value="' + pageInfo.pageNo + '" type="text" id="Pg" class="page_ipt"/>页';
        return text
    }, getQueryData: function (parameters) {
        var jsonData = {};
        if (this.showPageInfo) {
            jsonData = {"pageSize": this.page.pageSize, "pageNo": this.page.pageNo, "totalCount": this.page.totalCount}
        }
        jsonData = $.extend(jsonData, parameters);
        return jsonData
    }, query: function () {
        this.onLoad(this.searchParameters)
    }, initSortHead: function (opts) {
        var _parent = this;
        var headTrobj = $("#" + opts.head)[0];
        this.headInfos = [];
        for (var i = 0; i < opts.cells.length; i += 1) {
            var fieldName = opts.cells[i].name;
            var sortSpan = document.createElement("span");
            sortSpan.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
            $(headTrobj.cells[opts.cells[i].index]).append(sortSpan);
            this.headInfos[this.headInfos.length] = {
                index: opts.cells[i].index,
                name: opts.cells[i].name,
                sortSpan: sortSpan
            };
            $(headTrobj.cells[opts.cells[i].index]).bind("click", function () {
                _parent.setSortInfo(this.cellIndex);
                _parent.page.pageNo = 1;
                _parent.query()
            })
        }
    }, setSortInfo: function (cellIndex) {
        for (var i = 0; i < this.headInfos.length; i += 1) {
            var fieldName = this.headInfos[i].name;
            var sortSpan = this.headInfos[i].sortSpan;
            if (cellIndex == this.headInfos[i].index) {
                if (!this.sortInfo) {
                    this.sortInfo = {field: fieldName, fieldName: fieldName, sortOrder: 'desc'};
                    sortSpan.className = "sortDesc"
                } else {
                    if (this.sortInfo.fieldName == fieldName) {
                        if (this.sortInfo.sortOrder == "") {
                            sortSpan.className = "sortDesc";
                            this.sortInfo = {field: fieldName, fieldName: fieldName, sortOrder: "desc"}
                        } else if (this.sortInfo.sortOrder == "desc") {
                            sortSpan.className = "sortAsc";
                            this.sortInfo = {field: fieldName, fieldName: fieldName, sortOrder: "asc"}
                        } else {
                            sortSpan.className = "";
                            this.sortInfo = this.defaultSortInfo
                        }
                    } else {
                        sortSpan.className = "sortDesc";
                        this.sortInfo = {field: fieldName, fieldName: fieldName, sortOrder: 'desc'}
                    }
                }
            } else {
                sortSpan.className = ""
            }
        }
    }
};
function Pages() {
    this.pageNo = 1;
    this.totalPage = 0;
    this.totalCount = -1;
    this.pageSize = 10
}
function changeBackgroundColor(_val) {
    if ($(_val).attr("checked") == true) {
        $(_val).parent('td').parent('tr').addClass("checked")
    } else {
        $(_val).parent('td').parent('tr').removeClass("checked")
    }
}
function radioBackgroundColor() {
    var check = $("input:name='c'");
    check.each(function () {
        if ($(this).attr("checked") == true) {
            $(this).parent('td').parent('tr').addClass("checked")
        } else {
            $(this).parent('td').parent('tr').removeClass("checked")
        }
    })
}
function IdCheckBoxRenderer(idValue, value, param1, param2, isChecked) {
    var checked = (isChecked && isChecked(value));
    return '<label><input type="checkbox" name="c_check_box"	value=\"' + value + '\" ' + (checked ? 'checked="checked"' : '') + '" onclick="changeBackgroundColor(this);" ><span class="lbl"></span></label>'
}
function IdRadioRenderer(idValue, value) {
    return '<input type="radio" name="c_check_box"	value=\"' + value + '\" onclick="radioBackgroundColor();">'
}
function numRenderer(idValue, value, record, num) {
    return Number(num)
}
function getCheckedValuesByContainer(elementName, container) {
    elementName = elementName == undefined ? 'c_check_box' : elementName;
    var txt = "input[name=" + elementName + "]:checked";
    var stateStack = [];
    var tmp = container == undefined ? $(txt) : $(txt, container);
    if (tmp) {
        tmp.each(function () {
            stateStack.push($(this).val())
        })
    }
    return stateStack
}
function selectAll(value) {
    var eobj = $("input[name='c_check_box']");
    if (eobj.length) {
        for (var i = 0; i < eobj.length; i += 1) {
            if (eobj[i].type == "checkbox") {
                if (value) {
                    $(eobj[i]).parent('td').parent('tr').addClass("checked")
                } else {
                    $(eobj[i]).parent('td').parent('tr').removeClass("checked")
                }
                eobj[i].checked = value
            }
        }
    }
}