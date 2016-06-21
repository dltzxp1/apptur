'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('canton', {
                parent: 'entity',
                url: '/cantons',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.canton.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/canton/cantons.html',
                        controller: 'CantonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('canton');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('canton.detail', {
                parent: 'entity',
                url: '/canton/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.canton.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/canton/canton-detail.html',
                        controller: 'CantonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('canton');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Canton', function($stateParams, Canton) {
                        return Canton.get({id : $stateParams.id});
                    }]
                }
            })
            .state('canton.new', {
                parent: 'canton',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/canton/canton-dialog.html',
                        controller: 'CantonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    poblacion: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('canton', null, { reload: true });
                    }, function() {
                        $state.go('canton');
                    })
                }]
            })
            .state('canton.edit', {
                parent: 'canton',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/canton/canton-dialog.html',
                        controller: 'CantonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Canton', function(Canton) {
                                return Canton.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('canton', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('canton.delete', {
                parent: 'canton',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/canton/canton-delete-dialog.html',
                        controller: 'CantonDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Canton', function(Canton) {
                                return Canton.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('canton', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
