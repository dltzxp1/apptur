'use strict';

angular.module('appturApp').controller('SitiosDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sitios', 'Canton', 'Category',
        function($scope, $stateParams, $uibModalInstance, entity, Sitios, Canton, Category) {

        $scope.sitios = entity;
        $scope.cantons = Canton.query();
        $scope.categorys = Category.query();
        $scope.load = function(id) {
            Sitios.get({id : id}, function(result) {
                $scope.sitios = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:sitiosUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.sitios.id != null) {
                Sitios.update($scope.sitios, onSaveSuccess, onSaveError);
            } else {
                Sitios.save($scope.sitios, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
