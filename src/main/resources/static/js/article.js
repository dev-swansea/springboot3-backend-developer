const deleteBtn = document.getElementById("delete-btn");
const modifyBtn = document.getElementById("modify-btn");
const createBtn = document.getElementById("create-btn");

// 삭제 기능
if (deleteBtn) {
  deleteBtn.addEventListener("click", event => {
    let id = document.getElementById("article-id").value;
    const success = () => {
      alert("삭제 완료");
      location.replace("/articles");
    }

    const fail = () => {
      alert("삭제 실패");
      location.replace("/articles");
    }

    httpRequest("DELETE", `/api/articles/${id}`, null, success, fail);
  })
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
if (modifyBtn) {
  modifyBtn.addEventListener("click", e => {
    let param = new URLSearchParams(location.search);
    let id = param.get("id");
    const body = JSON.stringify({
      title  : document.getElementById("title").value,
      content: document.getElementById("content").value
    })

    const success = () => {
      alert("수정 완료");
      location.replace("/articles");
    }

    const fail = () => {
      alert("수정 실패");
      location.replace("/articles");
    }

    httpRequest("PUT", `/api/articles/${id}`, body, success, fail);

  })
}

if (createBtn) {
  createBtn.addEventListener("click", e => {
    const body = JSON.stringify({
      title  : document.getElementById("title").value,
      content: document.getElementById("content").value
    })

    const success = () => {
      alert("등록이 완료");
      location.replace("/articles");
    }

    const fail = () => {
      alert("등록 실패");
      location.replace("/articles");
    }

    httpRequest("POST", "/api/articles", body, success, fail);
  })
}

// 쿠키 가져오는 함수
const getCookie = (key) => {
  let result = null;
  let cookie = document.cookie.split(";");

  cookie.some((item) => {
    item = item.replace(" ", "");
    let dic = item.split("=");

    if (key === dic[0]) {
      result = dic[1];
      return true;
    }
  })
  return result;
}

// http 요청을 보내는 함수
const httpRequest = (method, url, body, success, fail) => {
  fetch(url, {
    method,
    headers: {
      // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
      Authorization : `Bearer ${localStorage.getItem("access_token")}`,
      "Content-Type": "application/json",
    },
    body
  }).then((response) => {
    const refresh_token = getCookie("refresh_token");
    
    if (response.status === 200 || response.status === 201) {
      return success(); // 알람창 띄우고 articles로 튕겨내버림
    }

    if (response.status === 401 && refresh_token) {
      fetch("/api/token", {
        method,
        headers: {
          Authorization : `Bearer ${localStorage.getItem("access_token")}`,
          "Content-Type": "application/json",
        },
        body   : JSON.stringify({
          refreshToken: getCookie("refresh_token"),
        })
      })
          .then(res => {
            if (res.ok) {
              return res.json();
            }
          })
          .then(result => {
            // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
            localStorage.setItem("access_token", result.accessToken);
            httpRequest(method, url, body, success, fail);
          })
          .catch(error => fail());
    } else {
      return fail();
    }
  })
}