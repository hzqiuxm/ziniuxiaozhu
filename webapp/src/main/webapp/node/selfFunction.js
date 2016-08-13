/**
 * Created by hzqiuxm on 2016/7/27 0027.
 */
'use strick'
    ;
//第一种写法
(function (){
    //独立的作用域，不会污染全局环境
})();


//第二种写法
(function (window,document) {
    //对于当前作用域，如果传入window传入，就不用依赖全局对象了
})(window,document);


//函数自执行过程,函数的括号表示形成一个表达式，否则无法执行
//形成表达式写法一
(function(){
    console.log(1111);
})();

//形成表达式写法二(逻辑表达式，数学运算符)
!function (){
 console.log(2222);
}();
