let setting = {
    treeId : "castingn",
    view: {
        selectedMulti: false,
        expandSpeed:"fast",
        showIcon :true
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

async function getUserCompanyManagerAuthTree(){
    let userCompanyDeptList = await getUserCompanyDept(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;

    let userInfo = await getUserInfo(); //회원 목록
    let userInfoList = userInfo.data;

    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        makeTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList);

        //$('#companyName').text(userCompany.cmpNm);
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

async function getUserInfo(){
    let paramData = {};

    const result = await post('/rest/mypage/getUserInfoByUserCompanySeq', paramData)
    if (result.status === "SUCCESS") {
        return result;

    } else {
        if(result.errorMessage) alert(result.errorMessage);
    }
}

//트리생성
function makeTree(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList){
    let zNodes=[];

    // 1뎁스 부서
    for(let i=0; i<userCompanyDeptStep1List.length; i++){
        let userCompanyDept = userCompanyDeptStep1List[i];

        let deptNodeJson = {
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
            , 'isDept': true
            , children:[]
        };
        zNodes.push(deptNodeJson);
    }

    // 2뎁스 부서
    for(let i=0; i<userCompanyDeptStep2List.length; i++){
        let userCompanyDept = userCompanyDeptStep2List[i];

        let childNodeJson = {
            id: userCompanyDept.deptSeq
            , pId: userCompanyDept.deptSeq
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
            , 'uname': userinfo.uname
            , 'uemail': userinfo.uemail
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'isUser': true
            , children:[]
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
            , pId: userCompanyDept.deptSeq
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
            , 'uname': userinfo.uname
            , 'uemail': userinfo.uemail
            , open: true
            , nocheck:false
            , 'uid': userinfo.uid
            , 'userCompanySeq': userinfo.userCompanySeq
            , 'udept': userinfo.udept
            , 'deptName': userinfo.deptName
            , 'isUser': true
            , iconSkin : "noimg"
            , children:[]
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

    $.fn.zTree.init($("#castingnTree"), setting, zNodes);
}

// 등록/수정
function crudUserCompanyManagerAuth() {
    let authSeq = $('#authSeq').val();
    let uid = $('#uid').val();

    let userCompanyManagerAuthFunctionList = []; //권한관리 - 권한설정
    $('[name=checkboxAuth]:checked').each(function(){
        let checkboxAuth = {};
        checkboxAuth.authFunction = $(this).val();
        userCompanyManagerAuthFunctionList.push(checkboxAuth);
    });

    let paramData = {
        'authName': $('#authName').val()
        , 'userCompanyManagerList': [{'uid':uid}]
        , 'userCompanyManagerAuthFunctionList': userCompanyManagerAuthFunctionList
        , 'uid': uid
        , 'authSeq': authSeq
    };

    post('/rest/mypage/crudUserCompanyManagerAuth', paramData)
        .then((result)=> {
            if (result.status === "SUCCESS") {
                getUserCompanyManagerAuthTree();
                $('.pop-close').click();

                location.href='/mypage/userCompanyManagerList';

            } else {
                if(result.errorMessage){
                    if( result.errorMessage === 'EXIST_IN_USER_COMPANY_MANAGER' ){
                        alert('기존에 등록된 관리자입니다.\n\n삭제 후 재등록 바랍니다.');
                    } else {
                        alert(result.errorMessage);
                    }
                }
            }
        });
}

//체크박스 선택시(선택전)
function beforeCheck(treeId, treeNode) {
    return true;
}

//체크박스 선택시
function onCheck(e, treeId, treeNode) {
    let zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkAllNodes(false); //전체선택 해제
    treeNode.checked = true;

    return true;
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

//체크된 노드 개수
/*function checkedNodeCount(treeId) {
    let zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getSelectedNodes();
    return nodes.length;
}*/

//체크된 항목 노드 가져오기
function getCheckedNodes(treeId){
    let zTree = $.fn.zTree.getZTreeObj(treeId),
        //nodes = zTree.getSelectedNodes();
        nodes = zTree.getChangeCheckedNodes();
    return nodes;
}
