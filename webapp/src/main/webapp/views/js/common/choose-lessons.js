/**
 * Created by hzqiuxm on 2016/8/12 0012.
 */
(function (angular) {
    'use strict'
    /**
     * 用于选课页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {

            $scope.template = { name: 'header.html', url: '/views/header.html'}

        }]);


})(angular)