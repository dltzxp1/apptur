'use strict';

angular.module('appturApp')
    .controller('ActividadDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Actividad, Sitios, Responsable) {
        $scope.actividad = entity;
        $scope.load = function (id) {
            Actividad.get({id: id}, function(result) {
                $scope.actividad = result;
            });
        };
        var unsubscribe = $rootScope.$on('appturApp:actividadUpdate', function(event, result) {
            $scope.actividad = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
