import * as angular from 'angular';
import {loginComponent} from './login.component';

angular.module('tn.auth')
    .component('tnLogin', loginComponent);
