'use strict';

angular.module('appturApp').controller('ResponsableDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Responsable', 'Actividad',
        function($scope, $stateParams, $uibModalInstance, entity, Responsable, Actividad) {

        $scope.responsable = entity;
        $scope.actividads = Actividad.query();
        $scope.load = function(id) {
            Responsable.get({id : id}, function(result) {
                $scope.responsable = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:responsableUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.responsable.id != null) {
                Responsable.update($scope.responsable, onSaveSuccess, onSaveError);
            } else {
                Responsable.save($scope.responsable, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
