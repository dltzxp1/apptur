'use strict';

angular.module('appturApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('responsable', {
                parent: 'entity',
                url: '/responsables',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.responsable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/responsable/responsables.html',
                        controller: 'ResponsableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('responsable');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('responsable.detail', {
                parent: 'entity',
                url: '/responsable/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'appturApp.responsable.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/responsable/responsable-detail.html',
                        controller: 'ResponsableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('responsable');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Responsable', function($stateParams, Responsable) {
                        return Responsable.get({id : $stateParams.id});
                    }]
                }
            })
            .state('responsable.new', {
                parent: 'responsable',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/responsable/responsable-dialog.html',
                        controller: 'ResponsableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    correo: null,
                                    telefono: null,
                                    celular: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('responsable', null, { reload: true });
                    }, function() {
                        $state.go('responsable');
                    })
                }]
            })
            .state('responsable.edit', {
                parent: 'responsable',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/responsable/responsable-dialog.html',
                        controller: 'ResponsableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Responsable', function(Responsable) {
                                return Responsable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('responsable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('responsable.delete', {
                parent: 'responsable',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/responsable/responsable-delete-dialog.html',
                        controller: 'ResponsableDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Responsable', function(Responsable) {
                                return Responsable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('responsable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
