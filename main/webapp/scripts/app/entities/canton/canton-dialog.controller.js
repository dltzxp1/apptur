'use strict';

angular.module('appturApp').controller('CantonDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Canton', 'Province',
        function($scope, $stateParams, $uibModalInstance, entity, Canton, Province) {

        $scope.canton = entity;
        $scope.provinces = Province.query();
        $scope.load = function(id) {
            Canton.get({id : id}, function(result) {
                $scope.canton = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:cantonUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.canton.id != null) {
                Canton.update($scope.canton, onSaveSuccess, onSaveError);
            } else {
                Canton.save($scope.canton, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
