import {IHttpService, IPromise} from 'angular';
import {UserForm} from '../user/model/user-form.model';
import * as $ from 'jquery';

export class AuthService {
    private http: IHttpService;

    constructor($http) {
        this.http = $http;
    }

    public checkExists(fieldName: string, value: string): IPromise<boolean> {
        return this.http.get('/exists/user', {
            params: {
                field: fieldName,
                value,
            },
        }).then((resp: any) => resp.data.exists);
    }

    public signup(userForm: UserForm) {
        return this.http.post('/signup', userForm);
    }

    public resendEmail(email: string, type: string) {
        return this.http({
            data: $.param({email, type}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            method: 'POST',
            url: '/resendEmail',
        });
    }
}

AuthService.$inject = ['$http'];