'use strict';

angular.module('appturApp')
    .controller('SitiosController', function ($scope, $state, Sitios, ParseLinks) {

        $scope.sitioss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Sitios.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.sitioss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.sitios = {
                nombre: null,
                descripcion: null,
                paginaweb: null,
                mail: null,
                facebook: null,
                twitter: null,
                direccion: null,
                telefono: null,
                latitud: null,
                longitud: null,
                id: null
            };
        };
    });
