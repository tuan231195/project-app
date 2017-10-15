import {LoginController} from './login.controller';

const template = require('./login.component.html');

export const loginComponent = {
    controller: LoginController,
    controllerAs: 'LoginController',
    template,
};