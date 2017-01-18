/**
 * Created by hzqiuxm on 2016/8/12 0012.
 */
(function (angular) {
    'use strict'
    /**
     * 用于每周谏言的句子展示
     *
     */
    angular.module('zn-index', [])
        .controller('nanController', ['$scope','$http', function ($scope, $http) {
            $scope.first = true;
            $scope.weekNans = [
                {id: 1, english: "Life is made up of small pleasures.", chinese: "生活由各种微小的幸福构成"},
                {id: 2, english: "Everything is good in its season", chinese: "万物逢时皆美好"}
            ];

            $scope.thisweek = $scope.weekNans[1];
            $scope.dailywords;

            $http({
                method: 'GET',
                url: '/daydayup'
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.dailywords = response.data;

            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.log("调用daydayup接口失败!");
            });



        }]);


})(angular)