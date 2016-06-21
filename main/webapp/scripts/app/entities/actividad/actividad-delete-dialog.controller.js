'use strict';

angular.module('appturApp')
	.controller('ActividadDeleteController', function($scope, $uibModalInstance, entity, Actividad) {

        $scope.actividad = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Actividad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
