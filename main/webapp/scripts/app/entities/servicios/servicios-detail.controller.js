'use strict';

angular.module('appturApp')
    .controller('ServiciosDetailController', function ($scope, $rootScope, $stateParams, entity, Servicios, Sitios, Catalogo) {
        $scope.servicios = entity;
        $scope.load = function (id) {
            Servicios.get({id: id}, function(result) {
                $scope.servicios = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:serviciosUpdate', function(event, result) {
            $scope.servicios = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
