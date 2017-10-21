import * as angular from 'angular';
import '../auth';
import '../user/user.module';
import {MainController} from './main.controller';
import {DebounceService} from './debounce.service';

angular.module('tn.app', ['ngAnimate', 'toastr']).controller(
    'MainController', MainController,
).factory('debounce', DebounceService)
    .config((toastrConfig) => {
        angular.extend(toastrConfig, {
            closeButton: true,
            containerId: 'toast-container',
            newestOnTop: true,
            tapToDismiss: true,
            target: 'body',
        });
    });
