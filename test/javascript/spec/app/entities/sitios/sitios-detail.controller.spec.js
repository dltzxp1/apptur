'use strict';

describe('Controller Tests', function() {

    describe('Sitios Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSitios, MockCanton, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSitios = jasmine.createSpy('MockSitios');
            MockCanton = jasmine.createSpy('MockCanton');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Sitios': MockSitios,
                'Canton': MockCanton,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("SitiosDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appturApp:sitiosUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
