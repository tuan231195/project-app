import {UserController} from './user.controller';

const template = require('./user.component.html');

export const userComponent = {
    controller: UserController,
    controllerAs: 'UserController',
    template,
};