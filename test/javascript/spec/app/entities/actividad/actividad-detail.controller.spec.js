'use strict';

describe('Controller Tests', function() {

    describe('Actividad Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockActividad, MockSitios, MockResponsable;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockActividad = jasmine.createSpy('MockActividad');
            MockSitios = jasmine.createSpy('MockSitios');
            MockResponsable = jasmine.createSpy('MockResponsable');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Actividad': MockActividad,
                'Sitios': MockSitios,
                'Responsable': MockResponsable
            };
            createController = function() {
                $injector.get('$controller')("ActividadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appturApp:actividadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
