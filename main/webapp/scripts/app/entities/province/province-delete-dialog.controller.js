'use strict';

angular.module('appturApp')
	.controller('ProvinceDeleteController', function($scope, $uibModalInstance, entity, Province) {

        $scope.province = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Province.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
