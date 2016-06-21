'use strict';

angular.module('appturApp')
    .controller('ResponsableDetailController', function ($scope, $rootScope, $stateParams, entity, Responsable, Actividad) {
        $scope.responsable = entity;
        $scope.load = function (id) {
            Responsable.get({id: id}, function(result) {
                $scope.responsable = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:responsableUpdate', function(event, result) {
            $scope.responsable = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
