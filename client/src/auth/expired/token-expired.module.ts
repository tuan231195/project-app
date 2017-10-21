import * as angular from 'angular';
import {tokenExpiredComponent} from './token-expired.component';

angular.module('tn.auth')
    .component('tnTokenExpired', tokenExpiredComponent);
