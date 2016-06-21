'use strict';

angular.module('appturApp')
    .factory('Actividad', function ($resource, DateUtils) {
        return $resource('api/actividads/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fecha = DateUtils.convertLocaleDateFromServer(data.fecha);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('ActividadService', function () {
        return  { 
            build: function(){
                var actividad = { responsabless: [] };
                return actividad;
            }
        };
    }).factory('ActividadServiceDel', function ($resource) {
        return $resource('api/responsablessdel/:id', {}, {
            
        });
    })
    ;;
