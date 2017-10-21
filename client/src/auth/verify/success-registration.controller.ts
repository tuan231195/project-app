import {AuthService} from '../auth.service';

export class SuccessRegistrationController {
    private toastr;
    private email: string;
    private authService: AuthService;
    private isLoading: boolean;

    constructor(toastr, email, authService) {
        this.toastr = toastr;
        this.email = email;
        this.authService = authService;
    }

    public resendEmail() {
        this.isLoading = true;
        this.authService.resendEmail(this.email, 'REGISTRATION')
            .then(() => {
                this.toastr.success('Email resent');
            }, (error) => {
                this.toastr.error('Failed to resend email');
            }).finally(() => {
            this.isLoading = false;
        });
    }
}

SuccessRegistrationController.$inject = ['toastr', 'email', 'authService'];
