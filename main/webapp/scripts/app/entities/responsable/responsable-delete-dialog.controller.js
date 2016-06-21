'use strict';

angular.module('appturApp')
	.controller('ResponsableDeleteController', function($scope, $uibModalInstance, entity, Responsable) {

        $scope.responsable = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Responsable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
