'use strict';

angular.module('appturApp')
    .controller('ProvinceDetailController', function ($scope, $rootScope, $stateParams, entity, Province) {
        $scope.province = entity;
        $scope.load = function (id) {
            Province.get({id: id}, function(result) {
                $scope.province = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:provinceUpdate', function(event, result) {
            $scope.province = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
