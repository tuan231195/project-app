import {SignupController} from './signup.controller';

const template = require('./signup.component.html');

export const signupComponent = {
    controller: SignupController,
    controllerAs: 'SignupController',
    template,
};