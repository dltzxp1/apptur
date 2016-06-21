'use strict';

angular.module('appturApp').controller('ActividadDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Actividad', 'Sitios', 'Responsable','ActividadService','ActividadServiceDel',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Actividad, Sitios, Responsable,ActividadService,ActividadServiceDel) {

        $scope.actividad = entity;
        if(entity.id===null){
            $scope.actividad= ActividadService.build();
        }
        $scope.sitioss = Sitios.query();
        $scope.responsables = Responsable.query();
        $scope.load = function(id) {
            Actividad.get({id : id}, function(result) {
                $scope.actividad = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appturApp:actividadUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.actividad.id != null) {
                Actividad.update($scope.actividad, onSaveSuccess, onSaveError);
            } else {
                Actividad.save($scope.actividad, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        $scope.datePickerForFecha = {};

        $scope.datePickerForFecha.status = {
            opened: false
        };

        $scope.datePickerForFechaOpen = function($event) {
            $scope.datePickerForFecha.status.opened = true;
        };

        $scope.setFoto = function ($file, actividad) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        actividad.foto = base64Data;
                        actividad.fotoContentType = $file.type;
                    });
                };
            }
        };

        $scope.addContact = function(actividad){
            $scope.actividad.responsabless.push({
                responsabless: []
            })
        };

        $scope.removeItem = function(index, array){
            if(array[index].id!==undefined){
                if(array[index].id>0){
                    console.log(array[index].id); 
                    ActividadServiceDel.delete({id: array[index].id},
                    function () {
                    });
                }
            }
            array.splice(index, 1);
        }; 


}]);
