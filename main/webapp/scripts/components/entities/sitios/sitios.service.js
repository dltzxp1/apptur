'use strict';

angular.module('appturApp')
    .factory('Sitios', function ($resource, DateUtils) {
        return $resource('api/sitioss/:id', {}, {
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
