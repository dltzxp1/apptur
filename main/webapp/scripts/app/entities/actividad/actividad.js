'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('actividad', {
                parent: 'entity',
                url: '/actividads',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.actividad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/actividad/actividads.html',
                        controller: 'ActividadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('actividad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('actividad.detail', {
                parent: 'entity',
                url: '/actividad/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.actividad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/actividad/actividad-detail.html',
                        controller: 'ActividadDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('actividad');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Actividad', function($stateParams, Actividad) {
                        return Actividad.get({id : $stateParams.id});
                    }]
                }
            })
            .state('actividad.new', {
                parent: 'actividad',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/actividad/actividad-dialog.html',
                        controller: 'ActividadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    direccion: null,
                                    fecha: null,
                                    foto: null,
                                    fotoContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('actividad', null, { reload: true });
                    }, function() {
                        $state.go('actividad');
                    })
                }]
            })
            .state('actividad.edit', {
                parent: 'actividad',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/actividad/actividad-dialog.html',
                        controller: 'ActividadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Actividad', function(Actividad) {
                                return Actividad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('actividad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('actividad.delete', {
                parent: 'actividad',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/actividad/actividad-delete-dialog.html',
                        controller: 'ActividadDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Actividad', function(Actividad) {
                                return Actividad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('actividad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
