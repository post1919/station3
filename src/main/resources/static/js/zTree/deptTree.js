let curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
let log, className = "dark", curDragNodes, autoExpandNode;
let member_regist_link_check = 0;

let setting = {
    treeId : "castingn",
    view: {
        addHoverDom: addHoverDom,
        addDiyDom: addDiyDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false,
        expandSpeed:"fast"
    },
    edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: showRemoveBtn,
        showRenameBtn: showRenameBtn
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
        beforeCollapse: beforeCollapse,
        beforeExpand: beforeExpand,
        onCollapse: onCollapse,
        onExpand: onExpand,
        onDrop:onDrop
    }
    , check: {
        enable: false,
        autoCheckTrigger: false,
        chkboxType: {"Y": "ps", "N":"ps"},
        chkStyle: "checkbox",
        nocheckInherit: false,
        chkDisabledInherit: false,
        radioType: "level"
    }
};

$(window).on("load", function (e) {
    //$('.edit').attr("rename", "그룹명 변경");
});

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

    let myNode = treeNodes[0];
    if( myNode.upperDept !== targetNode.upperDept ){
        alert('같은 부서 안에서만 이동가능합니다.');
        return false; //다른부서/다른상위 이동 불가
    }

    if( treeNodes[0].level === 0 ) return false; //최상위 노드는 이동 불가
    if( targetNode.level === 0 && moveType !== 'inner' ) return false; //최상의 노드의 전/후 위치로 이동 불가

    let lastLevelCheckFlag = lastLevelCheck(treeId, treeNodes, targetNode, moveType);
    if( !lastLevelCheckFlag ){
        alert('더 이상 하위부서를 생성할 수 없습니다.');
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
        if( treeNode.upperDept === 0 ){
            alert("최상위 부서는 삭제할 수 없습니다.");
            return false;
        } else if( treeNode.deptUserCnt > 0  ){
            alert("구성원이 존재하는 부서는 삭제할 수 없습니다.");
            return false;
        } else if( treeNode.deptStep == 2 && treeNode.children.length > 0  ){
            //최상위는 삭제 불가
            //최하위는 구성원만 확인
            //중간만 하위 부서가 있는지 확인
            alert("하위 부서가 존재하는 부서는 삭제할 수 없습니다.");
            return false;
        } else {
            //취소아니면 고고
            if (confirm("'" + treeNode.name + "' 삭제하시겠습니까?")) {
                let deptSeq = treeNode.deptSeq;
                let deptName = treeNode.deptName;
                let delFlag = "Y"; //사용 N, 삭제 Y

                let paramData = {
                    "deptSeq": deptSeq,
                    "delFlag": delFlag
                }

                crudUserCompanyDept('delete', deptSeq);
                return true;

            } else {
                return false;
            }
        }
    }
}

function onRemove(e, treeId, treeNode) {
    //showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}

function beforeRename(treeId, treeNode, newName, isCancel) {
    if( newName.length == 0 ){
        setTimeout(function () {
            let zTree = $.fn.zTree.getZTreeObj(treeId);
            zTree.cancelEditName();
        }, 0);

        return false;
    }

    //메뉴명이 바뀐경우만 고고
    if( treeNode.name != newName ) {
        let paramData = {
            'userCompanySeq': treeNode.userCompanySeq,
            'deptName': newName,
            'upperDept': treeNode.upperDept
        };
        post('/rest/mypage/getUserCompanyDeptByUserCompanySeq', paramData)
            .then(result => {
                if (result.status === 'SUCCESS') {
                    if (result.count > 0) {
                        alert('해당 이름의 부서명은 사용할 수 없습니다.');

                        setTimeout(function () {
                            let zTree = $.fn.zTree.getZTreeObj(treeId);
                            zTree.cancelEditName();
                        }, 0);

                        location.reload();

                        return false;
                    }
                }

                updateDeptName(treeNode, newName);
            }).catch(e => {
            alert(e);
            print('e', e);
        });
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

//수정버튼 노출여부
function showRemoveBtn(treeId, treeNode) {
    //return !treeNode.isFirstNode;
    return treeNode.deptStep !== 1; //첫번째 노드제외하고 노출
}

//수정버튼 노출여부
function showRenameBtn(treeId, treeNode) {
    //return !treeNode.isLastNode;
    //return !treeNode.isFirstNode;
    return treeNode.deptStep !== 1; //첫번째 노드제외하고 노출
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

function addDiyDom(treeId, treeNode) {
    let sObj = $("#" + treeNode.tId + "_span");

    //부서별 사용자 수
    if (treeNode.editNameFlag || $("#countLabel_"+treeNode.tId).length>0) return;

    //최상위 노드는 count-tit클래스 추가
    let deptUserCntStr = `<span class='count ${treeNode.deptStep === 1 ? "count-tit":""} ' id='countLabel_${treeNode.tId}' title=''> (${treeNode.deptUserCnt}) </span>`;

    sObj.after(deptUserCntStr);
    let deptUserCntStrBtn = $("#countLabel_"+treeNode.tId);

    //NEW 아이콘
    if (treeNode.editNameFlag || $("#newIcon_"+treeNode.tId).length>0) return;
    if( treeNode.authWaitUserCnt === 0 ) return; //승인대기 사용자 없는 부서는 제외

    //승인대기중인 구성원이 있는 경우만 NEW 아이콘 노출
    if ( _.isEmpty(treeNode.authStatus) ) {
        let newIconStr = "<span class='ico-new' id='newIcon_" + treeNode.tId + "' title=''> NEW </span>";
        sObj.after(newIconStr);
    }
}

function addHoverDom(treeId, treeNode) {
    let sObj = $("#countLabel_" + treeNode.tId); //카운트 영역

    //그룹 추가 버튼
    //let sObj = $("#" + treeNode.tId + "_span");

    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    let addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='그룹 추가' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    let addBtn = $("#addBtn_"+treeNode.tId);

    //구성원 관리 이동 버튼
    if (treeNode.editNameFlag || $("#moveBtn_"+treeNode.tId).length>0) return;
    let userCompanyMemberStr = "<span class='button move' id='moveBtn_" + treeNode.tId + "' title='구성원 관리 이동' onfocus='this.blur();'></span>";
    sObj.after(userCompanyMemberStr);
    let userCompanyMemberBtn = $("#moveBtn_"+treeNode.tId);

    //그룹 추가 버튼 > 클릭
    if (addBtn) addBtn.bind("click", function(){
        if( treeNode.level >= 2 ){
            alert("더 이상 하위부서를 생성할 수 없습니다.");
            return false;
        }

        let zTree = $.fn.zTree.getZTreeObj("castingnTree");
        let newNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"신규부서", deptStep:treeNode.deptStep, deptSeq: treeNode.deptSeq, deptName: "신규부서", deptUserCnt:0} );

        //기본정보 저장/수정
        insertDept(newNode[0]);

        return false;
    });

    //구성원 관리 버튼 클릭
    if (userCompanyMemberBtn){
        userCompanyMemberBtn.bind("click", function(){
            window.open('/mypage/userJoinApproval?deptSeq='+treeNode.deptSeq);
            return false;
        });
    }
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
    $("#moveBtn_"+treeNode.tId).unbind().remove();
    //$("#countLabel_"+treeNode.tId).unbind().remove(); //부서별 사용자 수
}

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

async function userCompanyProcess(){
    let result = await getUserCompanyDept();
    let userCompanyDeptCount = result.count;


    if( userCompanyDeptCount > 0 ){
        let userCompany = result.data.userCompany;
        let userCompanyDeptStep1List = result.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = result.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = result.data.userCompanyDeptStep3List;

        //조직 생성 여부 체크
        //기본은 회사명으로 생성되고 하위 부서 체크
        member_regist_link_check = userCompanyDeptStep2List.length;

        makeUserCompanyDeptTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List);

        //$('#companyName').text(userCompany.cmpNm);

        $('#userCompanyDeptTreeArea').show();
        $('#userCompanyDeptInfoArea').hide();
    } else {
        $('#userCompanyDeptTreeArea').hide();
        $('#userCompanyDeptInfoArea').show();
    }
}

async function getUserCompanyDept(deptName){
    let paramData = {'deptName':deptName};

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

    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let nodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': userCompanyDept.deptStep
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
            , 'deptUserCnt': userCompanyDept.deptUserCnt
            , 'authWaitUserCnt': userCompanyDept.authWaitUserCnt
            , children:[]
        };
        zNodes.push(nodeJson);
    }

    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.deptStep
            , name: userCompanyDept.deptName
            , open: true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'deptUserCnt': userCompanyDept.deptUserCnt
            , 'authWaitUserCnt': userCompanyDept.authWaitUserCnt
            , icon:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-org1.png"
            , iconOpen:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-org1.png"
            , iconClose:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-dept-yellow.png"
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
            , pId: userCompanyDept.deptStep
            , name: userCompanyDept.deptName
            , open: true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'deptUserCnt': userCompanyDept.deptUserCnt
            , 'authWaitUserCnt': userCompanyDept.authWaitUserCnt
            , icon:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-dept-green.png"
            , iconOpen:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-dept-green.png"
            , iconClose:"https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/tree/ico-dept-green.png"
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
    let today = new Date();
    let year = today.getFullYear();
    let month = ('0' + (today.getMonth() + 1)).slice(-2);
    let day = ('0' + today.getDate()).slice(-2);
    let hours = ('0' + today.getHours()).slice(-2);
    let minutes = ('0' + today.getMinutes()).slice(-2);
    let seconds = ('0' + today.getSeconds()).slice(-2);
    let dateString = year+month+day+hours+minutes+minutes+seconds;

    //let childNodes = node.getParentNode().children;
    let deptSeq   = node.deptSeq;
    let deptName  = node.deptName+"_"+dateString;
    let deptStep  = node.level+1;
    let deptSort  = 0;
    let upperDept = node.getParentNode().deptSeq;

    crudUserCompanyDept("insert", null, deptStep, deptName, deptSort, upperDept);
}