'use strict';

angular.module('appturApp')
	.controller('CatalogoDeleteController', function($scope, $uibModalInstance, entity, Catalogo) {

        $scope.catalogo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Catalogo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
