'use strict';

angular.module('appturApp')
	.controller('SitiosDeleteController', function($scope, $uibModalInstance, entity, Sitios) {

        $scope.sitios = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Sitios.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
