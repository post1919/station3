let curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
let log, className = "dark", curDragNodes, autoExpandNode;

let setting = {
    treeId : "castingn",
    view: {
        selectedMulti: false,
        expandSpeed:"fast"
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeDrop: beforeDrop,
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove,
        onRename: onRename,
        onClick: onClick,
        onCheck: onDeptCheck,
        beforeCollapse: beforeCollapse,
        beforeExpand: beforeExpand,
        onCollapse: onCollapse,
        onExpand: onExpand,
        onDrop:onDrop
    }
    , check: {
        enable: true,
        chkStyle: "checkbox",
        //chkboxType: { "Y": "", "N": "" }
        chkboxType : { "Y" : "s", "N" : "s" }
    }
};

$(window).on("load", function (e) {
    //$('.edit').attr("rename", "그룹명 변경");
});

function treeSettingType(type, settingJson){
    console.log("treeSettingType153")
    switch(type){
        case 'READONLY':
            break;

        default:
            setting.callback.onClick = function(event, treeId, treeNode, clickFlag){alert(treeNode.name);}
            break;
    }
}

function onDeptCheck(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("deptTree");
    var nodes = treeObj.getCheckedNodes(true);

    /*
    for (let i=0, l=nodes.length; i<l; i++) {
        if(treeNode.tId != nodes[i].tId) {
            treeObj.checkNode(nodes[i], false, false);
        }
    }
    */

/*
    let targetId ='';
    $.each(nodes, function (idx, row) {
        let childrens = row.children;
        if( nodes[idx].checked )
            targetId = nodes[idx].deptSeq;

        $.each(childrens, function (idx, row) {
            if( childrens[idx].upperDept == targetId ){
                treeObj.checkNode(childrens[idx], true, false);
            }
            if( childrens[idx].checked )
                targetId = childrens[idx].deptSeq;

            let childrens2 = row.children;
            $.each(childrens2, function (idx, row) {
                if( childrens2[idx].upperDept == targetId ){
                    treeObj.checkNode(childrens2[idx], true, false);
                }
                if( childrens2[idx].checked )
                    targetId = childrens2[idx].deptSeq;

                let childrens3 = row.children;
                $.each(childrens3, function (idx, row) {
                    if( delId == childrens3[idx].uid ){
                    }
                });
            });
        });
    });
*/

}

//마우스 클릭
function onClick(event, treeId, treeNode, clickFlag) {
    var treeObj = $.fn.zTree.getZTreeObj("deptTree");

    if( treeNode.checked == true ){
        treeObj.checkNode(treeNode, false, false);
    } else {
        treeObj.checkNode(treeNode, true, true);
    }

    var nodes = treeObj.getCheckedNodes(true);

    for (let i=0, l=nodes.length; i<l; i++) {
        if(treeNode.tId != nodes[i].tId) {
            treeObj.checkNode(nodes[i], false, false);
        }
    }
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
    let zTree = $.fn.zTree.getZTreeObj("deptTree");
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
    let zTree = $.fn.zTree.getZTreeObj("deptTree");
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
            let zTree = $.fn.zTree.getZTreeObj("deptTree");
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
    let zTree = $.fn.zTree.getZTreeObj("deptTree");
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
    $.fn.zTree.init($("#deptTree"), setting);
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

function expandNode(e) {
    let zTree = $.fn.zTree.getZTreeObj("deptTree"),
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

async function userCompanyProcess(){

    let result = await getUserCompanyDept();
    let userCompanyDeptCount = result.count;

    if( userCompanyDeptCount > 0 ){
        let userCompany = result.data.userCompany;
        let userCompanyDeptStep1List = result.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = result.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = result.data.userCompanyDeptStep3List;
        makeUserCompanyDeptTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List);

        //$('#companyName').text(userCompany.cmpNm);

        $('#userCompanyDeptTreeArea').show();
        $('#userCompanyDeptInfoArea').hide();
    } else {
        $('#userCompanyDeptTreeArea').hide();
        $('#userCompanyDeptInfoArea').show();
    }
}

async function getUserCompanyDept(){
    let paramData = {};

    const result = await post('/rest/mypage/getUserCompanyDept', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}

//트리생성
function makeUserCompanyDeptTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List){
    let zNodes=[];
        console.log("tree 들어옴")
    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let nodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': userCompanyDept.deptSeq
            , 'name': userCompanyDept.deptName
            , 'open': true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'delFlag': userCompanyDept.delFlag
            , children:[]
            , 'checked': false
            , "chkDisabled":false
        };
        zNodes.push(nodeJson);
    }
    console.log("zNodes")
    console.log(zNodes)
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
            , 'icon': 'https://resource.mall.castingn.com/static/images/ico-org.png'
            , 'checked': false
            , "chkDisabled":false
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
            , 'icon': 'https://resource.mall.castingn.com/static/images/ico-org.png'
            , 'checked': false
            , "chkDisabled":false
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

    $.fn.zTree.init($("#deptTree"), setting, zNodes);
}