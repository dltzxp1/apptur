'use strict';

describe('Controller Tests', function() {

    describe('Servicios Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockServicios, MockSitios, MockCatalogo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockServicios = jasmine.createSpy('MockServicios');
            MockSitios = jasmine.createSpy('MockSitios');
            MockCatalogo = jasmine.createSpy('MockCatalogo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Servicios': MockServicios,
                'Sitios': MockSitios,
                'Catalogo': MockCatalogo
            };
            createController = function() {
                $injector.get('$controller')("ServiciosDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appturApp:serviciosUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
