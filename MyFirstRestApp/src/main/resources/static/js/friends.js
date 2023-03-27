let stompClient;
let selectedUser;
let newMessages = new Map();
window.onload = getAllFriends();
const users = new Array();
function getStatus(array){
    for (let i = 0; i < array.length; i++) {
        $.get("/getStatus/?login=" + array[i], function (response) {
            let status = response;
            if(status === "AUTHORIZED"){$(`#status_${array[i]}`).css('color','green');}
            else {$(`#status_${array[i]}`).css('color','red')}
        });
    }
}
function connect(){
    window.location.href='/connect';
}
function getPage(user){
    localStorage.setItem('name',user);
    connect();
}
function getAllFriends() {
    $.get("/getAllFriends", function (response) {
        let friends = response;
        let friendsTemplateHTML = "";
        for (let i = 0; i < friends.length; i++) {
            friendsTemplateHTML = friendsTemplateHTML + '<a href="#" onclick="getPage(\'' + friends[i] + '\')"><div class="clearfix">\n' +
                '                <img src="/image/free-icon-man.png" width="55px" height="55px" alt="avatar" />\n' +
                '                <div class="about">\n' +
                '                    <div style="color: #75b1e8" id="userNameAppender_' + friends[i] + '" class="name">' + friends[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline" id="status_' + friends[i] + '"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div></a><hr/>';
            users.push(friends[i]);
        }
        $('#friendsList').html(friendsTemplateHTML);
        getStatus(users)
    });
}