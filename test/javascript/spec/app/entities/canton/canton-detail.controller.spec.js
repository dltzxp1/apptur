'use strict';

describe('Controller Tests', function() {

    describe('Canton Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCanton, MockProvince;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCanton = jasmine.createSpy('MockCanton');
            MockProvince = jasmine.createSpy('MockProvince');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Canton': MockCanton,
                'Province': MockProvince
            };
            createController = function() {
                $injector.get('$controller')("CantonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appturApp:cantonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
