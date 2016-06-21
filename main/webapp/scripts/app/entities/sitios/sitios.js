'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sitios', {
                parent: 'entity',
                url: '/sitioss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.sitios.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sitios/sitioss.html',
                        controller: 'SitiosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sitios');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sitios.detail', {
                parent: 'entity',
                url: '/sitios/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.sitios.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sitios/sitios-detail.html',
                        controller: 'SitiosDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sitios');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Sitios', function($stateParams, Sitios) {
                        return Sitios.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sitios.new', {
                parent: 'sitios',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sitios/sitios-dialog.html',
                        controller: 'SitiosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('sitios', null, { reload: true });
                    }, function() {
                        $state.go('sitios');
                    })
                }]
            })
            .state('sitios.edit', {
                parent: 'sitios',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sitios/sitios-dialog.html',
                        controller: 'SitiosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Sitios', function(Sitios) {
                                return Sitios.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sitios', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('sitios.delete', {
                parent: 'sitios',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sitios/sitios-delete-dialog.html',
                        controller: 'SitiosDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Sitios', function(Sitios) {
                                return Sitios.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sitios', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
