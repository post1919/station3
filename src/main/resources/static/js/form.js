

$(document).ready(function(){
  $(document).on("input", ".kit-input input", function(){
    if($(this).val()===''){
      $(this).removeClass("valued");
    } else {
      $(this).addClass("valued");
    }

  });

  $(".kit-input input").each(function (el, index, array) {
    if($(this).is(":disabled")) {
      $(this).closest(".kit-input").addClass("disabled");
    }
  });

  $("ul.list-checkSquare > li input[type=checkbox]").each(function(){
    $(this).closest("li").append("<span>"+$(this).val()+"</span>");
  });
  /*$("ul.list-checkSquare > li.all span").append("이 모든 업무를 저 혼자 하고 있어요!");*/


  // 배송지 관리
  $("ul.list-destination > li").each(function(){

    if($(this).find("input[type=radio]").is(":checked")) {
      $(this).addClass("on");
    }

    $(this).on("click", function(){
      $("ul.list-destination > li").removeClass("on");
      $(this).addClass("on");
      $(this).find("input[type=radio]").prop("checked", true);
    });
  });
});
