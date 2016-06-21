'use strict';

angular.module('appturApp')
    .factory('Responsable', function ($resource, DateUtils) {
        return $resource('api/responsables/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
