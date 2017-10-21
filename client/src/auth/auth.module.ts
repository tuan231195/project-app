import * as angular from 'angular';
import './auth.scss';
import {AuthService} from './auth.service';

angular.module('tn.auth', []).service('authService', AuthService);