function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    let str = {"parentId": questionId, "content": content, "type": 1};
    $.post({
        url: "/comment",
        data: JSON.stringify(str),
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        success: function (data) {
            if(data.code == 200){
                $("#comment_section").hide();
            }else {
                if(data.code == 2003){
                    var isAccepted = confirm(data.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=9c7781539c114fa76a78&redirect_uri=http://localhost:5200/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                }else{
                    alert(data.message);
                }
            }
        }
    });
}