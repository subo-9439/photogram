/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

    // (0) 현재 로긴한 사용자 아이디
//    let principalId = $("${#authentication.principal.user.id}").val();

    // (1) 스토리 로드하기
let page = 0;

function storyLoad() {
//        console.log('storyLoad')
//        console.log(principalId);
    $.ajax({
        url: `/api/image?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log(res);
        res.data.content.forEach((image)=>{
        let storyItem = getStoryItem(image);
        $("#storyList").append(storyItem);
        });
    }).fail(error => {
        console.log("오류", error);
    });
}
    
storyLoad();

function getStoryItem(image) {
    console.log('getStoryItem')
    let item = `<div class="story-list-item">
    <div class="sl-item-header">
        <div>
            <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                onerror="" />
        </div>
        <div>${image.user.username}</div>
    </div>
    </div>`;
    return item;
}
// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	//console.log("윈도우 scrollTop", $(window).scrollTop());
	//console.log("문서의 높이", $(document).height());
	//console.log("윈도우 높이", $(window).height());
	
	let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
	//console.log(checkNum);
	
	if(checkNum < 1 && checkNum > -1){
		page++;
		storyLoad();
	}
});

// (3) 좋아요, 안좋아요
function toggleLike() {
	let likeIcon = $("#storyLikeIcon-1");
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl-item-contents-comment" id="storyCommentItem-2"">
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}



