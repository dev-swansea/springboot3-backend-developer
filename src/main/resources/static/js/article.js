const deleteBtn = document.getElementById("delete-btn");
const modifyBtn = document.getElementById("modify-btn");
const createBtn = document.getElementById("create-btn");
console.log(createBtn)

// 삭제 기능
if (deleteBtn) {
  deleteBtn.addEventListener("click", event => {
    let id = document.getElementById("article-id").value;
    fetch(`/api/articles/${id}`, {
      method: "DELETE"
    })
        .then(() => {
          alert("삭제가 완료되었다.")
          location.replace("/articles");
        })
  })
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
if (modifyBtn) {
  modifyBtn.addEventListener("click", e => {
    let param = new URLSearchParams(location.search);
    let id = param.get("id");
    fetch(`/api/articles/${id}`, {
      method : "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body   : JSON.stringify({
        title  : document.getElementById("title").value,
        content: document.getElementById("content").value
      })
    })
        .then(() => {
          alert("수정이 완료되었습니다.");
          location.replace(`/articles/${id}`)
        })
  })
}

if (createBtn) {
  createBtn.addEventListener("click", e => {
    fetch(`/api/articles`, {
      method : "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body   : JSON.stringify({
        title  : document.getElementById("title").value,
        content: document.getElementById("content").value
      })
    })
        .then(() => {
          alert("등록 완료되었다.");
          location.replace("/articles")
        })
  })
}