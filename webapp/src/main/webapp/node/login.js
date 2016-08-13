/**
 * Created by hzqiuxm on 2016/7/25 0025.
 */
'use strick'
var http = require('http');

var server = http.createServer(function (request, response) {

    var requesturl = request.url;
    switch (requesturl) {
        case '/signin':
            siginin(request,response);
            break;
        case '/post' :
            post(request,response);
            break;
        default :
            response.writeHead(404, {})
            response.end();
    }


});

//启动监听服务
server.listen(8080, function (error) {
    console.log('8080 is OK!');
});

function siginin(request, response) {
    console.log('请求登录页面');
    response.end();
}

function post(request, response) {
    console.log('请求POST页面');
    response.end();
}

