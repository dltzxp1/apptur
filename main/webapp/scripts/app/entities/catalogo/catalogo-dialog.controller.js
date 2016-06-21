'use strict';

angular.module('appturApp').controller('CatalogoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Catalogo',
        function($scope, $stateParams, $uibModalInstance, entity, Catalogo) {

        $scope.catalogo = entity;
        $scope.load = function(id) {
            Catalogo.get({id : id}, function(result) {
                $scope.catalogo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:catalogoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.catalogo.id != null) {
                Catalogo.update($scope.catalogo, onSaveSuccess, onSaveError);
            } else {
                Catalogo.save($scope.catalogo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
