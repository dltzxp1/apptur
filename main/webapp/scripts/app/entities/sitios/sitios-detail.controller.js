'use strict';

angular.module('appturApp')
    .controller('SitiosDetailController', function ($scope, $rootScope, $stateParams, entity, Sitios, Canton, Category) {
        $scope.sitios = entity;
        $scope.load = function (id) {
            Sitios.get({id: id}, function(result) {
                $scope.sitios = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:sitiosUpdate', function(event, result) {
            $scope.sitios = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
