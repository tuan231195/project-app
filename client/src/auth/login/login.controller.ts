import {Observable} from 'rxjs';
import * as $ from 'jquery';
import {UserForm} from '../../user/model/user-form.model';

export class LoginController {
    private errors = {username: '', password: ''};
    private user: UserForm;

    constructor() {
        this.user = new UserForm();
        this.initEvent();
    }

    private initEvent() {
       console.log('test');
    }
}
