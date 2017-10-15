import * as angular from 'angular';
import {userComponent} from './user.component';

angular.module('tn.users', [])
    .component('tnUser', userComponent);
