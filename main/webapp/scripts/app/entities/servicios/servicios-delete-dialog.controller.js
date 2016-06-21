'use strict';

angular.module('appturApp')
	.controller('ServiciosDeleteController', function($scope, $uibModalInstance, entity, Servicios) {

        $scope.servicios = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Servicios.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
