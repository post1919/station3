let curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
let log, className = "dark", curDragNodes, autoExpandNode;

let setting = {
    treeId : "castingn",
    view: {
        selectedMulti: false,
        expandSpeed:"fast"
    },
    edit: {
        enable: false,
        editNameSelectAll: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: function(event, treeId, treeNode, clickFlag){
            /*if( treeNode.isUser ){
                $('#uid').val(treeNode.uid);
                $('#userCompanyManager').val(treeNode.name);

                $('.pop-close').click();
            }*/

            return false;
        },
        beforeCheck: beforeCheck,
        onCheck: onCheck
    },
    check: {
        autoCheckTrigger: false,
        chkboxType: {"Y": "ps", "N":"ps"},
        chkStyle: "checkbox",
        enable: true,
        nocheckInherit: false,
        chkDisabledInherit: true,
        radioType: "level"
    }
};

$(window).on("load", function (e) {
    //$('.edit').attr("rename", "그룹명 변경");
});

//체크박스 선택시(선택전)
function beforeCheck(treeId, treeNode) {
    return true;
}

//체크박스 선택시
function onCheck(e, treeId, treeNode) {
    let zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkAllNodes(false); //전체선택 해제
    treeNode.checked = true;

    onCheckCallback(e, treeId, treeNode); //개발하는 화면에서 작성할 함수

    return false;
}

//체크된 노드 개수
function checkedNodeCount(treeId) {
    let checkedCount = 0;
    let zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getChangeCheckedNodes();
    for (let i=0, l=nodes.length; i<l; i++) {
        let isChecked = nodes[i].checked;
        if(isChecked) checkedCount++;
    }

    return checkedCount;
}

function treeSettingType(type, settingJson){
    switch(type){
        case 'READONLY':
            setting.edit.enable = false;
            setting.edit.editNameSelectAll = false;
            setting.edit.showRemoveBtn = function(){};
            setting.edit.showRenameBtn = function(){};
            setting.view.addHoverDom = function(){};
            setting.callback.onClick = function(event, treeId, treeNode, clickFlag){alert(treeNode.name);}
            break;

        case 'MULTI_CHECK':
            setting.check.enable = true;
            setting.check.chkDisabledInherit = false;
            setting.callback.onClick = function(event, treeId, treeNode, clickFlag){alert(treeNode.name);}
            break;

        case 'MULTI_CHECK_INHERIT':
            setting.check.enable = true;
            setting.check.chkDisabledInherit = true;
            break;

        case 'CUSTOM':
            setting = settingJson;
            break;

        default:
            setting.callback.onClick = function(event, treeId, treeNode, clickFlag){alert(treeNode.name);}
            break;
    }
}

//마우스 클릭
function onClick(event, treeId, treeNode, clickFlag) {
    //onClickCallback(event, treeId, treeNode, clickFlag); //개발하는 화면에서 작성할 함수
    return false;
};

//마우스드래그중
function beforeDrag(treeId, treeNodes) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes." );
    for (let i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            curDragNodes = null;
            return false;
        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
            curDragNodes = null;
            return false;
        }
    }

    curDragNodes = treeNodes;
    return true;
}

//드래그 후 드롭할때
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
    //showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"));

    if( treeNodes[0].level === 0 ) return false; //최상의 노드는 이동 불가
    if( targetNode.level === 0 && moveType !== 'inner' ) return false; //최상의 노드의 전/후 위치로 이동 불가

    let lastLevelCheckFlag = lastLevelCheck(treeId, treeNodes, targetNode, moveType);
    if( !lastLevelCheckFlag ){
        alert('그룹 이동 후 LEVEL이 3이상일 수 없습니다.');
        return false;
    }

    return true;
}

function lastLevelCheck(treeId, treeNodes, targetNode, moveType){
    let lastLevelCheckFlag = true;
    let treeLastLevel=0; //드래그 노드의 최대 level
    let targetLastLevel=0; //드롭할 타겟 노드의 최대 level
    let lastChildLevel=0;

    if( moveType !== 'inner' ) return true;

    if (treeNodes.length > 0) {
        let treeNode = treeNodes[0];
        treeLastLevel = treeNode.level;

        if ( !_.isEmpty(treeNode.children) ) {
            let childNode = treeNode.children[0];
            treeLastLevel = childNode.level;

            if (childNode.length > 0) {
                let childChildNode = childNode.children[0];
                treeLastLevel = childChildNode.level;
            }
        }
    }

    lastChildLevel = treeLastLevel + targetNode.level;

    if (lastChildLevel >= 3) {
        lastLevelCheckFlag = false;
    }

    return lastLevelCheckFlag;
}

//드래그 후 드롭할때(beforeDrop함수에서 true일때 넘어옴)
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    //className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
    //showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"))

    /*print('moveType', moveType);
    print('myNode', treeNodes[0]);
    print('targetNode', targetNode);*/

    let myNode = treeNodes[0];
    let deptSeq = myNode.deptSeq;
    let deptStep;
    let deptName = myNode.deptName;
    let deptSort;
    let upperDept;
    switch(moveType){
        case 'inner':
            deptStep = targetNode.deptStep+1;
            deptSort = 1;
            upperDept = targetNode.deptSeq;
            break;
        case 'prev':
            deptStep = targetNode.deptStep;
            deptSort = targetNode.deptSort;
            upperDept = targetNode.upperDept;
            break;
        case 'next':
            deptStep = targetNode.deptStep;
            deptSort = targetNode.deptSort+1;
            upperDept = targetNode.upperDept;
            break;
    }

    if( !_.isNull(moveType) ) {
        crudUserCompanyDept('move', deptSeq, deptStep, deptName, deptSort, upperDept)
    }
}

function dropPrev(treeId, nodes, targetNode) {
    let pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (let i=0,l=curDragNodes.length; i<l; i++) {
            let curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}

function dropInner(treeId, nodes, targetNode) {
    if (targetNode && targetNode.dropInner === false) {
        return false;
    } else {
        for (let i=0,l=curDragNodes.length; i<l; i++) {
            if (!targetNode && curDragNodes[i].dropRoot === false) {
                return false;
            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                return false;
            }
        }
    }
    return true;
}

function dropNext(treeId, nodes, targetNode) {
    let pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (let i=0,l=curDragNodes.length; i<l; i++) {
            let curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}

function beforeDragOpen(treeId, treeNode) {
    autoExpandNode = treeNode;
    return true;
}

//수정아이콘 클릭시
function beforeEditName(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    let zTree = $.fn.zTree.getZTreeObj("castingnTree");
    zTree.selectNode(treeNode);
    setTimeout(function() {
        //if (confirm(treeNode.name + "' 수정하시겠습니까?")) {
        setTimeout(function() {
            zTree.editName(treeNode);
        }, 0);
        //}
    }, 0);
    return false;
}

//삭제 아이콘 클릭
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    let zTree = $.fn.zTree.getZTreeObj("castingnTree");
    zTree.selectNode(treeNode);

    if( typeof treeNode.deptSeq == "undefined" ){
        //return면 onRemove호출함
        return true;

    } else {
        //취소아니면 고고
        if( confirm("'" + treeNode.name + "' 삭제하시겠습니까?") ){
            let deptSeq  = treeNode.deptSeq;
            let deptName = treeNode.deptName;
            let delFlag  = "Y"; //사용 N, 삭제 Y

            let paramData = {
                "deptSeq" : deptSeq,
                "delFlag" : delFlag
            }

            crudUserCompanyDept('delete', deptSeq);
            return true;

        } else {
            return false;
        }
    }
}

function onRemove(e, treeId, treeNode) {
    //showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}

function beforeRename(treeId, treeNode, newName, isCancel) {
    className = (className === "dark" ? "":"dark");
    //showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    if (newName.length == 0) {
        setTimeout(function() {
            let zTree = $.fn.zTree.getZTreeObj("castingnTree");
            zTree.cancelEditName();
            //alert("Node name can not be empty.");
        }, 0);
        return false;
    }

    //메뉴명이 바뀐경우만 고고
    if( treeNode.name != newName ){
        updateDeptName(treeNode, newName);
    }

    return true;
}

//메뉴명 수정
function updateDeptName(treeNode, newName){
    let deptSeq = treeNode.deptSeq;
    let deptStep = treeNode.deptStep;
    let deptName = newName;

    let paramData = {
        "deptSeq"   : deptSeq
        , "deptName" : deptName
    }

    crudUserCompanyDept('update', deptSeq, deptStep, deptName);
}

//메뉴명 변경한후(엔터)
function onRename(e, treeId, treeNode, isCancel) {
    //console.log(treeNode);
    //showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
}

function showRemoveBtn(treeId, treeNode) {
    return true;
    //return !treeNode.isFirstNode;
}

function showRenameBtn(treeId, treeNode) {
    return true;
    //return !treeNode.isLastNode;
}

function beforeCollapse(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
    return (treeNode.collapse !== false);
}

function onCollapse(event, treeId, treeNode) {
    //showLog("[ "+getTime()+" onCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
}

function beforeExpand(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    //showLog("[ "+getTime()+" beforeExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
    return (treeNode.expand !== false);
}

function onExpand(event, treeId, treeNode) {
    //showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
}

function expandNodes(nodes) {
    if (!nodes) return;
    curStatus = "expand";
    let zTree = $.fn.zTree.getZTreeObj("castingnTree");
    for (let i=0, l=nodes.length; i<l; i++) {
        zTree.expandNode(nodes[i], true, false, false);
        if (nodes[i].isParent && nodes[i].zAsync) {
            expandNodes(nodes[i].children);
        } else {
            goAsync = true;
        }
    }
}

function reset() {
    if (!check()) {
        return;
    }
    asyncForAll = false;
    goAsync = false;
    //$("#demoMsg").text("");
    $.fn.zTree.init($("#castingnTree"), setting);
}

function check() {
    if (curAsyncCount > 0) {
        //	$("#demoMsg").text(demoMsg.async);
        return false;
    }
    return true;
}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}

function getTime() {
    let now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}

let newCount = 1;

function addHoverDom(treeId, treeNode) {
    let sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    let addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='그룹 추가' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    let btn = $("#addBtn_"+treeNode.tId);

    //아이콘눌러서 노드추가
    if (btn) btn.bind("click", function(){
        if( treeNode.level >= 2 ){
            alert("3 DEPTH이상은 만들수 없습니다.");
            return false;
        }

        let zTree = $.fn.zTree.getZTreeObj("castingnTree");
        let newNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"", deptStep:treeNode.deptStep, deptSeq: treeNode.deptSeq, deptName: ""} );

        //기본정보 저장/수정
        insertDept(newNode[0]);

        return false;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function selectAll() {
    let zTree = $.fn.zTree.getZTreeObj("castingnTree");
    zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}

function expandNode(e) {
    let zTree = $.fn.zTree.getZTreeObj("castingnTree"),
        type = e.data.type,
        nodes = zTree.getSelectedNodes();
    if (type.indexOf("All")<0 && nodes.length == 0) {
        alert("Please select one parent node at first...");
    }

    if (type == "expandAll") {
        zTree.expandAll(true);
    } else if (type == "collapseAll") {
        zTree.expandAll(false);
    } else {
        let callbackFlag = $("#callbackTrigger").attr("checked");
        for (let i=0, l=nodes.length; i<l; i++) {
            zTree.setting.view.fontCss = {};
            if (type == "expand") {
                zTree.expandNode(nodes[i], true, null, null, callbackFlag);
            } else if (type == "collapse") {
                zTree.expandNode(nodes[i], false, null, null, callbackFlag);
            } else if (type == "toggle") {
                zTree.expandNode(nodes[i], null, null, null, callbackFlag);
            } else if (type == "expandSon") {
                zTree.expandNode(nodes[i], true, true, null, callbackFlag);
            } else if (type == "collapseSon") {
                zTree.expandNode(nodes[i], false, true, null, callbackFlag);
            }
        }
    }
}

function crudUserCompanyDept(type, deptSeq, deptStep, deptName, deptSort, upperDept) {
    let paramData = {'type': type, 'deptSeq': deptSeq};

    switch(type){
        case 'insert' :
        case 'move'   : paramData.deptName = deptName; paramData.deptStep = deptStep; paramData.upperDept = upperDept; paramData.deptSort = deptSort; break;
        case 'update' : paramData.deptName = deptName; break;
        case 'delete' : break;

    }

    post('/rest/mypage/crudUserCompanyDept', paramData)
        .then((result)=> {
            if (result.status === "SUCCESS") {
                userCompanyProcess();

            } else {
                if(result.errorMessage) alert(result.errorMessage);
            }
        });
}

async function userCompanyProcess(userCompanySeq){
    let result = await getUserCompanyDept(userCompanySeq);
    let userCompanyDeptCount = result.count;

    if( userCompanyDeptCount > 0 ){
        let userCompany = result.data.userCompany;
        let userCompanyDeptStep1List = result.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = result.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = result.data.userCompanyDeptStep3List;

        makeUserCompanyDeptTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List);

        $('#userCompanyDeptTreeArea').show();
        $('#userCompanyDeptInfoArea').hide();
    } else {
        $('#userCompanyDeptTreeArea').hide();
        $('#userCompanyDeptInfoArea').show();
    }
}

async function getUserCompanyDept(userCompanySeq){
    let paramData = {'userCompanySeq':userCompanySeq};

    const result = await post('/rest/join/getUserCompanyDeptByUserCompanySeq', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}

//트리생성
function makeUserCompanyDeptTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List){
    let zNodes=[];

    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let nodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': userCompanyDept.deptSeq
            , 'name': userCompanyDept.deptName
            , 'open': true
            , nocheck:true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'delFlag': userCompanyDept.delFlag
            , children:[]
        };
        zNodes.push(nodeJson);
    }

    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.deptSeq
            , name: userCompanyDept.deptName
            , open: true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , children:[]
        };

        $.each(zNodes, function(idx, row){
            if(zNodes[idx].id === childNodeJson.upperDept){
                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    for(let i=0; i<userCompanyDeptStep3List.length; i++){
        let userCompanyDept = userCompanyDeptStep3List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.deptSeq
            , name: userCompanyDept.deptName
            , open: true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
        };

        $.each(zNodes, function(idx, row){
            let childrens = row.children;
            $.each(childrens, function(idx, row) {
                if(childrens[idx].id === childNodeJson.upperDept){
                    childrens[idx].children.push(childNodeJson);
                }
            });
        });
    }

    $.fn.zTree.init($("#castingnTree"), setting, zNodes);
}

//그룹 추가
function insertDept(node){
    //let childNodes = node.getParentNode().children;
    let deptSeq   = node.deptSeq;
    let deptName  = node.deptName;
    let deptStep  = node.level+1;
    let deptSort  = 1;
    let upperDept = node.getParentNode().deptSeq;

    crudUserCompanyDept("insert", null, deptStep, deptName, deptSort, upperDept);
}