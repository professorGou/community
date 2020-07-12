//提交回复
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2target(questionId, 1, content)
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    let str = {"parentId": targetId, "content": content, "type": type};
    $.post({
        url: "/comment",
        data: JSON.stringify(str),
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else {
                if (data.code == 2003) {
                    let isAccepted = confirm(data.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=9c7781539c114fa76a78&redirect_uri=http://localhost:5200/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(data.message);
                }
            }
        }
    });
}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

//展开二级评论
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);

    //展开二级评论
    comments.toggleClass("in");

    if (comments.hasClass("in")) {
        $.getJSON("/comment/" + id, function (data) {
            var subCommentContainer = $("#comment-" + id);
            //判断该标签下是否有子元素
            if (subCommentContainer.children().length != 1) {
                //有则直接展开二级评论
                e.classList.add("active");
            } else {
                //没有子元素，则动态生成
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<images/>", {
                        "class": "media-object images-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            }

            e.classList.add("active");
        });
    } else {
        e.classList.remove("active");
    }
    //标记二级评论展开状态
    // e.setAttribute("data-collapse", "in");
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(value) {
    var value = value.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}