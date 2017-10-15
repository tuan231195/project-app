import * as angular from 'angular';
import '../auth';
import '../user/user.module';
import {MainController} from './main.controller';

angular.module('tn.app', []).controller(
    'MainController', MainController,
);
