'use strict';

angular.module('appturApp')
    .controller('CatalogoDetailController', function ($scope, $rootScope, $stateParams, entity, Catalogo) {
        $scope.catalogo = entity;
        $scope.load = function (id) {
            Catalogo.get({id: id}, function(result) {
                $scope.catalogo = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:catalogoUpdate', function(event, result) {
            $scope.catalogo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
