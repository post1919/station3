let vSetting = {
    treeId : "castingn",
    view: {
        selectedMulti: false,
        expandSpeed:"fast",
        showIcon :true
        //showIcon: showIconForTree
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
            return false;
        },
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
}

let vSettingMultiSelect = {
    treeId : "castingn",
    view: {
        selectedMulti: false,
        expandSpeed:"fast",
        showIcon :true
        //showIcon: showIconForTree
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
            return false;
        },
        onAsyncSuccess: myOnAsyncSuccess
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
}

let vSettingMultiSelectPinclub = {
    treeId : "castingn",
    view: {
        selectedMulti: true,
        expandSpeed:"fast",
        showIcon :true
        //showIcon: showIconForTree
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
            return false;
        }
    },
    check: {
        autoCheckTrigger: false,
        chkboxType: {"Y": "s", "N":"s"},
        chkStyle: "checkbox",
        enable: true,
        nocheckInherit: false,
        chkDisabledInherit: true,
        radioType: "level"
    }
}

//체크박스 선택시
function onCheck(e, treeId, treeNode) {
    let zTree = $.fn.zTree.getZTreeObj(treeId);
    let nodes = zTree.getCheckedNodes(true);

    console.log("onCheck:"+treeId)

    if(nodes.length > 1 ) {
        zTree.checkAllNodes(false); //전체선택 해제
        treeNode.checked = true;
    }

    return true;
}


function showIconForTree(treeId, treeNode) {
    return !treeNode.isUser;
};

// 결재선 유저 트리 ( 단일 셀렉트 )
async function approverProcess(){

    let userCompanyDeptList = await getUserCompanyDept2(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;

    let userInfo = await getUserInfo2(); //회원 목록
    let userInfoList = userInfo.data;

    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        makeTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList, 'approverTree');
    }
}

// 예산 사용자 트리 (멀티 셀렉트)
async function budgetUserMultiSelectTree(treeUid){

    let userCompanyDeptList = await getUserCompanyDept2(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;
    //console.log("approverProcess > userCompanyDeptList")
    //console.log(userCompanyDeptList)
    //console.log("approverProcess > userCompanyDeptCount")
    //console.log(userCompanyDeptCount)

    let userInfo = await getUserInfo2(); //회원 목록
    let userInfoList = userInfo.data;
    //console.log("userInfo > userInfo")
    //console.log(userInfo)
    //console.log("userInfoList > userInfoList")
    //console.log(userInfoList)
    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        makeTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList, treeUid);
    }
}

// 권한사용자 트리 ( 선택된 부서의 유저는 disabled 처리 )
async function budgetUserTree(udepts){

    let userCompanyDeptList = await getUserCompanyDept2(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;

    let userInfo = await getUserInfo2(); //회원 목록
    let userInfoList = userInfo.data;

    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        if( udepts != null && udepts.length > 0 ){
            return makeBudgetUserTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList, udepts);
        }
    }

}

// 결재선 유저 트리 ( 단일 셀렉트 )
async function pinClubProcess(mpsPk, userCompanySeq){

    let userCompanyDeptList = await getUserCompanyDept2(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;

    let userInfo = await getUserInfoPinclubService(mpsPk, userCompanySeq); //회원 목록, 서비스 미신청자만
    let userInfoList = userInfo.data;

    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        console.log("pinclub tree")

        makeTreePinclub(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList, 'approverTree');
    }
}

function myOnAsyncSuccess(event, treeId, treeNode, msg) {
    alert(msg);
};

async function getUserCompanyDept2(){
    let paramData = {};

    const result = await post('/rest/mypage/getUserCompanyDept', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}
/* 구성원 불러오기*/
async function getUserInfo2(){
    let paramData = {};

    const result = await post('/rest/mypage/getUserInfoByUserCompanySeq', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}

/* 구성원 불러오기*/
async function getUserInfoPinclubService(mpsPk, userCompanySeq){
    let paramData = {
        'mpsPk' : mpsPk,
        'userCompanySeq' : userCompanySeq
    };

    const result = await post('/rest/mypage/getUserInfoPinclubService', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}

//트리생성 ( 부서 선택된 유저는 disable 처리 )
function makeBudgetUserTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList, udepts){
    let zNodes=[];
    let checkUid = [];

    // 1뎁스 부서
    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let deptNodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': 0
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
            , 'isDept': true
            , children:[]
        };
        zNodes.push(deptNodeJson);
    }

    // 1뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        $.each(zNodes, function(idx, row){

            let childNodeJson = {
                id: userinfo.upk
                , pId: zNodes[idx].pId
                , name: userinfo.uname
                , nocheck:false
                , 'uid': userinfo.uid
                , 'userCompanySeq': userinfo.userCompanySeq
                , 'udept': userinfo.udept
                , 'isUser': true
                , 'showIcon ': false
                , "chkDisabled": false
                , iconSkin : "noimg"
            };

            if(zNodes[idx].id === childNodeJson.udept){
                for(let x=0; x< udepts.length; x++) {
                    childNodeJson.chkDisabled = true;
                }
                for(let x=0; x< udepts.length; x++) {
                    if ( udepts[x] == zNodes[idx].id){
                        childNodeJson.chkDisabled = false;
                        childNodeJson.checked = true;
                        checkUid.push({ uid: childNodeJson.uid, uname: childNodeJson.name, udept: childNodeJson.udept });
                    }
                }
                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 부서
    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
        };

        $.each(zNodes, function(idx, row){
            if(zNodes[idx].id === childNodeJson.upperDept){
                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , children:[]
            , "chkDisabled":false
            , iconSkin : "noimg"
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                if (childrens[idx].id === childNodeJson.udept) {
                    for(let x=0; x< udepts.length; x++) {
                        childNodeJson.chkDisabled = true;
                    }
                    for(let x=0; x< udepts.length; x++) {
                        if ( udepts[x] == childrens[idx].id){
                            childNodeJson.chkDisabled = false;
                            childNodeJson.checked = true;
                            checkUid.push({ uid: childNodeJson.uid, uname: childNodeJson.name, udept: childNodeJson.udept });
                        }
                    }
                    childrens[idx].children.push(childNodeJson);
                }
            });
        });
    }

    // 3뎁스 부서
    for(let i=0; i<userCompanyDeptStep3List.length; i++){
        let userCompanyDept = userCompanyDeptStep3List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
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

    // 3뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , iconSkin : "noimg"
            , children:[]
            , "chkDisabled":false
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                let childrens2 = row.children;
                $.each(childrens2, function (idx, row) {
                    if (childrens2[idx].id === childNodeJson.udept) {
                        for(let x=0; x< udepts.length; x++) {
                            childNodeJson.chkDisabled = true;
                        }
                        for(let x=0; x< udepts.length; x++) {
                            if ( udepts[x] == childrens2[idx].id){
                                childNodeJson.chkDisabled = false;
                                childNodeJson.checked = true;
                                checkUid.push({ uid: childNodeJson.uid, uname: childNodeJson.name, udept: childNodeJson.udept });
                            }
                        }
                        childrens2[idx].children.push(childNodeJson);
                    }
                });
            });
        });
    }
    $.fn.zTree.init($("#authUserTree"), vSettingMultiSelect, zNodes);

    return checkUid;
}

//트리생성
function makeTreePinclub(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList , treeUid){
    let zNodes=[];
    console.log("트리 생성 pinclub")
    // 1뎁스 부서
    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let deptNodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': 0
            , 'name': userCompanyDept.deptName
            , 'open': true
            , nocheck:false
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'delFlag': userCompanyDept.delFlag
            , 'isDept': true
            , children:[]
        };
        zNodes.push(deptNodeJson);
    }


    // 1뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];


        $.each(zNodes, function(idx, row){

            let childNodeJson = {
                id: userinfo.upk
                , pId: zNodes[idx].pId
                , name: userinfo.uname
                , nocheck:false
                , 'uid': userinfo.uid
                , 'userCompanySeq': userinfo.userCompanySeq
                , 'udept': userinfo.udept
                , 'isUser': true
                , 'showIcon ': false
                , "chkDisabled": false
                , iconSkin : "noimg"
            };

            if(zNodes[idx].id === childNodeJson.udept){

                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 부서
    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:false
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
        };

        $.each(zNodes, function(idx, row){
            if(zNodes[idx].id === childNodeJson.upperDept){
                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , children:[]
            , "chkDisabled":false
            , iconSkin : "noimg"
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                if (childrens[idx].id === childNodeJson.udept) {
                    childrens[idx].children.push(childNodeJson);
                }
            });
        });
    }

    // 3뎁스 부서
    for(let i=0; i<userCompanyDeptStep3List.length; i++){
        let userCompanyDept = userCompanyDeptStep3List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:false
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
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

    // 3뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , iconSkin : "noimg"
            , children:[]
            , "chkDisabled":false
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                let childrens2 = row.children;
                $.each(childrens2, function (idx, row) {
                    if (childrens2[idx].id === childNodeJson.udept) {
                        childrens2[idx].children.push(childNodeJson);
                    }
                });
            });
        });
    }
    //console.log("vSetting");
    //console.log(vSetting);
    //console.log("zNodes");
    //console.log(zNodes);
    $.fn.zTree.init($("#"+treeUid), vSettingMultiSelectPinclub, zNodes);
}




//트리생성
function makeTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList , treeUid){
    let zNodes=[];
    console.log("트리 생성 ")
    // 1뎁스 부서
    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let deptNodeJson = {
            'id': userCompanyDept.deptSeq
            , 'pId': 0
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
            , 'isDept': true
            , children:[]
        };
        zNodes.push(deptNodeJson);
    }


    // 1뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];


        $.each(zNodes, function(idx, row){

            let childNodeJson = {
                id: userinfo.upk
                , pId: zNodes[idx].pId
                , name: userinfo.uname
                , nocheck:false
                , 'uid': userinfo.uid
                , 'userCompanySeq': userinfo.userCompanySeq
                , 'udept': userinfo.udept
                , 'isUser': true
                , 'showIcon ': false
                , "chkDisabled": false
                , iconSkin : "noimg"
            };

            if(zNodes[idx].id === childNodeJson.udept){

                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 부서
    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
        };

        $.each(zNodes, function(idx, row){
            if(zNodes[idx].id === childNodeJson.upperDept){
                zNodes[idx].children.push(childNodeJson);
            }
        });
    }

    // 2뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , children:[]
            , "chkDisabled":false
            , iconSkin : "noimg"
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                if (childrens[idx].id === childNodeJson.udept) {
                    childrens[idx].children.push(childNodeJson);
                }
            });
        });
    }

    // 3뎁스 부서
    for(let i=0; i<userCompanyDeptStep3List.length; i++){
        let userCompanyDept = userCompanyDeptStep3List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.upperDept
            , name: userCompanyDept.deptName
            , open: true
            , nocheck:true
            , 'userCompanySeq': userCompanyDept.userCompanySeq
            , 'deptSeq': userCompanyDept.deptSeq
            , 'deptName': userCompanyDept.deptName
            , 'upperDept': userCompanyDept.upperDept
            , 'deptStep': userCompanyDept.deptStep
            , 'deptGroup': userCompanyDept.deptGroup
            , 'deptSort': userCompanyDept.deptSort
            , 'isDept': true
            , children:[]
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

    // 3뎁스 회원
    for(let i=0; i<userInfoList.length; i++){
        let userinfo = userInfoList[i];

        let childNodeJson = {
            id: userinfo.upk
            , pId: userinfo.upk
            , name: userinfo.uname
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , iconSkin : "noimg"
            , children:[]
            , "chkDisabled":false
        };

        $.each(zNodes, function (idx, row) {
            let childrens = row.children;
            $.each(childrens, function (idx, row) {
                let childrens2 = row.children;
                $.each(childrens2, function (idx, row) {
                    if (childrens2[idx].id === childNodeJson.udept) {
                        childrens2[idx].children.push(childNodeJson);
                    }
                });
            });
        });
    }
    //console.log("vSetting");
    //console.log(vSetting);
    //console.log("zNodes");
    //console.log(zNodes);
    $.fn.zTree.init($("#"+treeUid), vSetting, zNodes);
}