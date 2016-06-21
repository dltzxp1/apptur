'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('servicios', {
                parent: 'entity',
                url: '/servicioss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.servicios.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/servicios/servicioss.html',
                        controller: 'ServiciosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('servicios');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('servicios.detail', {
                parent: 'entity',
                url: '/servicios/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.servicios.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/servicios/servicios-detail.html',
                        controller: 'ServiciosDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('servicios');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Servicios', function($stateParams, Servicios) {
                        return Servicios.get({id : $stateParams.id});
                    }]
                }
            })
            .state('servicios.new', {
                parent: 'servicios',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/servicios/servicios-dialog.html',
                        controller: 'ServiciosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    codigo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('servicios', null, { reload: true });
                    }, function() {
                        $state.go('servicios');
                    })
                }]
            })
            .state('servicios.edit', {
                parent: 'servicios',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/servicios/servicios-dialog.html',
                        controller: 'ServiciosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Servicios', function(Servicios) {
                                return Servicios.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('servicios', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('servicios.delete', {
                parent: 'servicios',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/servicios/servicios-delete-dialog.html',
                        controller: 'ServiciosDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Servicios', function(Servicios) {
                                return Servicios.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('servicios', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
