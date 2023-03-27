let stompClient;
let selectedUser;
let newMessages = new Map();
function connectToChat(userName) {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}
window.onload = selectUser(getCookie());
function getCookie() {
    return localStorage.getItem('name');
}
 function getLoginUser(){
    let userLogin;
    $.get("/getUser",function (response)
    {
        userLogin=response
    });
    return userLogin;
}
function registration() {
   // let user = getLoginUser();
    let user = $('#userName').text();
    connectToChat(user);
    console.log('console connect: ' + user)
}
 function selectUser(userName) {
    registration();
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

