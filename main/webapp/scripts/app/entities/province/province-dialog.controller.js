'use strict';

angular.module('appturApp').controller('ProvinceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Province',
        function($scope, $stateParams, $uibModalInstance, entity, Province) {

        $scope.province = entity;
        $scope.load = function(id) {
            Province.get({id : id}, function(result) {
                $scope.province = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:provinceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.province.id != null) {
                Province.update($scope.province, onSaveSuccess, onSaveError);
            } else {
                Province.save($scope.province, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
