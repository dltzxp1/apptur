'use strict';

angular.module('appturApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


