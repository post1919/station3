
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
        }

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
// 구성원 트리
async function userProcess(){
    let userCompanyDeptList = await getUserCompanyDept2(); //그룹(부서) 목록
    let userCompanyDeptCount = userCompanyDeptList.count;
    console.log("approverProcess > userCompanyDeptList")
    console.log(userCompanyDeptList)
    console.log("approverProcess > userCompanyDeptCount")
    console.log(userCompanyDeptCount)

    let userInfo = await getUserInfo2(); //회원 목록
    let userInfoList = userInfo.data;
    console.log("userInfo > userInfo")
    console.log(userInfo)
    console.log("userInfoList > userInfoList")
    console.log(userInfoList)
    if( userCompanyDeptCount > 0 ){
        let userCompany = userCompanyDeptList.data.userCompany;

        let userCompanyDeptStep1List = userCompanyDeptList.data.userCompanyDeptStep1List;
        let userCompanyDeptStep2List = userCompanyDeptList.data.userCompanyDeptStep2List;
        let userCompanyDeptStep3List = userCompanyDeptList.data.userCompanyDeptStep3List;

        makeTree1(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList);

        //$('#companyName').text(userCompany.cmpNm);
    }
}
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

function makeTree1(userCompanyDeptStep1List, userCompanyDeptStep2List, userCompanyDeptStep3List, userInfoList){
    /*userCompanyDeptStep1List */
    console.log("=========list==========")
    console.log(userCompanyDeptStep1List)
    console.log(userCompanyDeptStep2List)
    console.log(userCompanyDeptStep3List)
    console.log(userInfoList)
    console.log("=========end==========")
    var zNodes = [];
    for(var dept1 of userCompanyDeptStep1List){

        var obj = {
            id : dept1.deptSeq,
            pId : dept1.upperDept,
            name : dept1.deptName,
            open : true,
            uid : '',
            position : 'dept1'
        }

        zNodes.push(obj);
    }
    for(var dept2 of userCompanyDeptStep2List){
        var obj = {
            id : dept2.deptSeq,
            pId : dept2.upperDept,
            name : dept2.deptName,
            open : true,
            uid : '',
            position : 'dept2'
        }
        zNodes.push(obj);
    }
    for(var dept3 of userCompanyDeptStep3List){
        var obj = {
            id : dept3.deptSeq,
            pId : dept3.upperDept,
            name : dept3.deptName,
            open : true,
            uid : '',
            position : 'dept3'
        }
        zNodes.push(obj);
    }
    for(var user of userInfoList){
        var obj = {
            id : user.upk,
            pId : user.udept,
            name : user.uname,
            open : false,
            uid : user.uid,
            position : 'user'
        }
        zNodes.push(obj);
    }




  /*  var zNodes =[
        { id:'1', pId:'0', name:"can check 1", open:true},
        { id:'11', pId:'1', name:"can check 1-1", open:true},
        { id:'111', pId:'11', name:"can check 1-1-1"},
        { id:'112', pId:'11', name:"can check 1-1-2"},
        { id:'12', pId:'1', name:"can check 1-2", open:true},
        { id:'121', pId:'12', name:"can check 1-2-1"},
        { id:'122', pId:'12', name:"can check 1-2-2"},
        { id:'2', pId:'0', name:"can check 2", open:true},
        { id:'21', pId:'2', name:"can check 2-1"},
        { id:'22', pId:'2', name:"can check 2-2", open:true},
        { id:'221', pId:'22', name:"can check 2-2-1"},
        { id:'222', pId:'22', name:"can check 2-2-2"},
        { id:'23', pId:'2', name:"can check 2-3"}
    ];
*/

    $.fn.zTree.init($("#approverTree"), vSetting, zNodes);
}