'use strict';

angular.module('appturApp').controller('ServiciosDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Servicios', 'Sitios', 'Catalogo',
        function($scope, $stateParams, $uibModalInstance, entity, Servicios, Sitios, Catalogo) {

        $scope.servicios = entity;
        $scope.sitioss = Sitios.query();
        $scope.catalogos = Catalogo.query();
        $scope.load = function(id) {
            Servicios.get({id : id}, function(result) {
                $scope.servicios = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:serviciosUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.servicios.id != null) {
                Servicios.update($scope.servicios, onSaveSuccess, onSaveError);
            } else {
                Servicios.save($scope.servicios, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
