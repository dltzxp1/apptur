'use strict';

angular.module('appturApp')
    .factory('Catalogo', function ($resource, DateUtils) {
        return $resource('api/catalogos/:id', {}, {
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
