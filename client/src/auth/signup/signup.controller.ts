import {UserForm} from '../../user/model/user-form.model';
import {UserValidator} from '../../user/service/validator.service';

export class SignupController {
    private user: UserForm;
    private formFields: Map<string, object>;

    private USER_FIELD = 'username';

    private EMAIL_FIELD = 'email';

    private PASSWORD_FIELD = 'password';

    private CONFIRM_PASSWORD_FIELD = 'confirmPassword';

    constructor() {
        this.user = new UserForm();
        this.formFields = new Map([[this.USER_FIELD, {
            error: '',
            valid: false,
            validators: [UserValidator.validateRequired.bind(this, this.user, this.USER_FIELD, () => this.formFields)],
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

    public onInputChange(fieldName) {

        const field: any = this.formFields.get(fieldName);
        if (!field) {
            return;
        }
        for (const validator of field.validators) {
            if (validator()) {
                break;
            }
        }
    }

    public isNotValid() {
        return Array.from(this.formFields.values()).some((value: any) => {
            return !value.valid;
        });
    }
}