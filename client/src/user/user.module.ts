import * as angular from 'angular';
import {userComponent} from './user.component';

const moduleName = angular.module('tn.users', [])
    .component('tnUser', userComponent).name;
