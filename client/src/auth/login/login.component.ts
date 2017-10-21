import {LoginController} from './login.controller';

const template = require('./login.component.html');

export const loginComponent = {
    bindings: {
        error: '@',
        verifySuccess: '<',
    },
    controller: LoginController,
    controllerAs: 'LoginController',
    template,
    transclude: true,
};