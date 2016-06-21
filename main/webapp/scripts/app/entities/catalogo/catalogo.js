'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('catalogo', {
                parent: 'entity',
                url: '/catalogos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.catalogo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catalogo/catalogos.html',
                        controller: 'CatalogoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catalogo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('catalogo.detail', {
                parent: 'entity',
                url: '/catalogo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.catalogo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catalogo/catalogo-detail.html',
                        controller: 'CatalogoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catalogo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Catalogo', function($stateParams, Catalogo) {
                        return Catalogo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('catalogo.new', {
                parent: 'catalogo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catalogo/catalogo-dialog.html',
                        controller: 'CatalogoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    tipo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('catalogo', null, { reload: true });
                    }, function() {
                        $state.go('catalogo');
                    })
                }]
            })
            .state('catalogo.edit', {
                parent: 'catalogo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catalogo/catalogo-dialog.html',
                        controller: 'CatalogoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Catalogo', function(Catalogo) {
                                return Catalogo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('catalogo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('catalogo.delete', {
                parent: 'catalogo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catalogo/catalogo-delete-dialog.html',
                        controller: 'CatalogoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Catalogo', function(Catalogo) {
                                return Catalogo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('catalogo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
