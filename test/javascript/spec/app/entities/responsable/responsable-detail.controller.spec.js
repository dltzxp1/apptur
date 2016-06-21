'use strict';

describe('Controller Tests', function() {

    describe('Responsable Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResponsable, MockActividad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResponsable = jasmine.createSpy('MockResponsable');
            MockActividad = jasmine.createSpy('MockActividad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Responsable': MockResponsable,
                'Actividad': MockActividad
            };
            createController = function() {
                $injector.get('$controller')("ResponsableDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appturApp:responsableUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
