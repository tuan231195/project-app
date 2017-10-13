import * as angular from 'angular';
import 'angular-mocks';

describe('Testing user component', () => {

    beforeEach(() => {
        angular.mock.module('tn.app');
    });

    it('should run correctly', () => {
        expect(true).toBe(true);
    });
});