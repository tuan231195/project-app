import {UserForm} from '../../user/model/user-form.model';
import {UserValidator} from '../../user/service/validator.service';
import {IHttpService, IPromise, IWindowService} from 'angular';
import {ValidationController} from '../../core/validation.controller';
import {AuthService} from '../auth.service';
import {ucFirst} from '../../core/utils/strings';
import {debounce} from 'rxjs/operator/debounce';

export class SignupController extends ValidationController {
    private user: UserForm;

    private USER_FIELD = 'username';

    private FIRSTNAME_FIELD = 'firstName';

    private LASTNAME_FIELD = 'lastName';

    private EMAIL_FIELD = 'email';

    private PASSWORD_FIELD = 'password';

    private CONFIRM_PASSWORD_FIELD = 'confirmPassword';

    private http: IHttpService;

    private window: IWindowService;

    private authService: AuthService;

    private debounce;

    private requestDebounce;

    private isLoading: boolean;

    private toastr;

    constructor($http, $window, authService, debounceFactory, toastr) {
        super();
        this.user = new UserForm();
        this.http = $http;
        this.window = $window;
        this.authService = authService;
        this.debounce = debounceFactory;
        this.requestDebounce = new Map([[
            this.EMAIL_FIELD, debounceFactory(this.checkExists.bind(this, this.EMAIL_FIELD), 1000, false),
        ], [
            this.USER_FIELD, debounceFactory(this.checkExists.bind(this, this.USER_FIELD), 1000, false),
        ]]);
        this.formFields = new Map([[this.FIRSTNAME_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this,
                this.user, this.FIRSTNAME_FIELD, () => this.formFields)],
        }], [this.LASTNAME_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this,
                this.user, this.LASTNAME_FIELD, () => this.formFields)],
        }], [this.USER_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this,
                this.user, this.USER_FIELD, () => this.formFields)],
        }], [this.EMAIL_FIELD, {
            error: '',
            valid: false,
            validators: [
                UserValidator.validateRequired.bind(this, this.user, this.EMAIL_FIELD, () => this.formFields),
                UserValidator.validateEmail.bind(this, this.user, this.EMAIL_FIELD, () => this.formFields),
            ],
        }], [this.PASSWORD_FIELD, {
            error: '',
            valid: false,
            validators: [
                UserValidator.validateRequired.bind(this, this.user, this.PASSWORD_FIELD, () => this.formFields),
                UserValidator.validatePassword.bind(this, this.user, this.PASSWORD_FIELD,
                    {minLength: 4}, () => this.formFields),
                UserValidator.validateConfirm.bind(this, this.user,
                    this.PASSWORD_FIELD, this.CONFIRM_PASSWORD_FIELD, () => this.formFields),
            ],
        }], [this.CONFIRM_PASSWORD_FIELD, {
            error: '',
            valid: false,
            validators: [
                UserValidator.validateRequired.bind(this, this.user,
                    this.CONFIRM_PASSWORD_FIELD, () => this.formFields),
                UserValidator.validateConfirm.bind(this, this.user,
                    this.PASSWORD_FIELD, this.CONFIRM_PASSWORD_FIELD, () => this.formFields),
            ],
        }]]);
    }

    public signup() {
        this.isLoading = true;
        this.authService.signup(this.user).then(() => {
            this.window.location.href = '/registrationSuccess';
            this.isLoading = false;
        }, () => {
            this.toastr.error('Failed to signup', 'Signup Error');
        }).finally(() => {
            this.isLoading = false;
        });
    }

    public onInputChange(fieldName: string) {
        super.onInputChange(fieldName);
        if (fieldName === this.USER_FIELD || fieldName === this.EMAIL_FIELD) {
            const value: any = this.formFields.get(fieldName);
            value.valid = false;
            this.requestDebounce.get(fieldName)().then((exists: boolean) => {
                if (exists) {
                    value.valid = false;
                    value.error = ucFirst(fieldName) + ' is already taken.';
                } else {
                    value.valid = true;
                    value.error = '';
                }
            });
        }
    }

    private checkExists(fieldName) {
        return this.authService.checkExists(fieldName, this.user[fieldName]);
    }
}

SignupController.$inject = ['$http', '$window', 'authService', 'debounce', 'toastr'];