import {UserForm} from '../../user/model/user-form.model';
import {UserValidator} from '../../user/service/validator.service';
import {ValidationController} from '../../core/validation.controller';
import {ILocationService} from 'angular';

export class LoginController extends ValidationController {
    private user: UserForm;
    private USERNAME_FIELD = 'username';
    private PASSWORD_FIELD = 'password';
    private toastr;
    private verifySuccess: boolean;
    private location: ILocationService;

    constructor(toastr, location) {
        super();
        this.toastr = toastr;
        this.location = location;
        this.user = new UserForm();
        this.formFields = new Map([[this.USERNAME_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this,
                this.user, this.USERNAME_FIELD, () => this.formFields)],
        }], [this.PASSWORD_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this,
                this.user, this.PASSWORD_FIELD, () => this.formFields)],
        }]]);
    }

    public $onInit() {
        if (this.verifySuccess) {
            this.toastr.success('Please login again', 'Confirmation Success');
        }
    }
}

LoginController.$inject = ['toastr', '$location'];
