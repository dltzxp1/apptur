'use strict';

angular.module('appturApp')
    .controller('CantonDetailController', function ($scope, $rootScope, $stateParams, entity, Canton, Province) {
        $scope.canton = entity;
        $scope.load = function (id) {
            Canton.get({id: id}, function(result) {
                $scope.canton = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:cantonUpdate', function(event, result) {
            $scope.canton = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
