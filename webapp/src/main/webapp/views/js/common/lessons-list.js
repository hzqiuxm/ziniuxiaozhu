/**
 * Created by hzqiuxm on 2016/8/22 0022.
 */
(function (angular) {
    'use strict'
    /**
     * 课程展现页相关操作
     *
     */
    angular.module('lessons_lists', [])
        .controller('lesssonsController', ['$scope','$http','$templateCache', function ($scope,$http,$templateCache) {

            $scope.four = true;

            $scope.getinfo = function(){

                $scope.response = null;
                $scope.code = null;
                $scope.method = 'GET';
                $scope.url = '/views/header.html';

                $http({method: $scope.method, url: $scope.url, cache: $templateCache}).
                then(function(response) {
                    $scope.status = response.status;
                    $scope.data = response.data;
                }, function(response) {
                    $scope.data = response.data || 'Request failed';
                    $scope.status = response.status;
                });

            };

        }]);


})(angular)