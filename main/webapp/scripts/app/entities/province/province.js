'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('province', {
                parent: 'entity',
                url: '/provinces',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.province.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/province/provinces.html',
                        controller: 'ProvinceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('province');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('province.detail', {
                parent: 'entity',
                url: '/province/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.province.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/province/province-detail.html',
                        controller: 'ProvinceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('province');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Province', function($stateParams, Province) {
                        return Province.get({id : $stateParams.id});
                    }]
                }
            })
            .state('province.new', {
                parent: 'province',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/province/province-dialog.html',
                        controller: 'ProvinceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    capital: null,
                                    poblacion: null,
                                    region: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('province', null, { reload: true });
                    }, function() {
                        $state.go('province');
                    })
                }]
            })
            .state('province.edit', {
                parent: 'province',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/province/province-dialog.html',
                        controller: 'ProvinceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Province', function(Province) {
                                return Province.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('province', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('province.delete', {
                parent: 'province',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/province/province-delete-dialog.html',
                        controller: 'ProvinceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Province', function(Province) {
                                return Province.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('province', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
