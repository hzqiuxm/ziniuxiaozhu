/**
 * Created by hzqiuxm on 2016/8/12 0012.
 */
(function (angular) {
    'use strict'
    /**
     * 用于首页相关操作
     *
     */
    angular.module('zn-index', [])
        .controller('nanController', ['$scope', function ($scope) {

            $scope.first = true;
            $scope.weekNans = [
                {id: 1, english: "Life is made up of small pleasures.", chinese: "生活由各种微小的幸福构成"},
                {id: 2, english: "Everything is good in its season", chinese: "万物逢时皆美好"}
            ];

            $scope.thisweek = $scope.weekNans[1];

        }]);


})(angular)