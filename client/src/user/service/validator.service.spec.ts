import {UserValidator} from './validator.service';

describe('Testing UserValidator', () => {
    let obj;
    beforeEach(() => {
        obj = {number: 9.5, integer: 9, password: 'abc123@', email: 'vdtn359@gmail.com'};
    });
    it('should test number correctly', () => {
        let error = UserValidator.validateNumber(obj, 'number');
        expect(error).toBeUndefined();
        obj.number = 'abc';
        error = UserValidator.validateNumber(obj, 'number');
        expect(error).toBeDefined();
    });

    it('should test integer correctly', () => {
        let error = UserValidator.validateInteger(obj, 'integer');
        expect(error).toBeUndefined();
        obj.integer = '-1.5';
        error = UserValidator.validateInteger(obj, 'integer');
        expect(error).toBeDefined();
    });

    it('should test password correctly', () => {
        let error = UserValidator.validatePassword(obj, 'password', {minLength: 4});
        expect(error).toBeUndefined();
        obj.password = '12';
        error = UserValidator.validatePassword(obj, 'password', {minLength: 4});
        expect(error).toBeDefined();
        obj.password = 'abc123';
        error = UserValidator.validatePassword(obj, 'password', {minLength: 4});
        expect(error).toBeDefined();
    });

    it('should test email correctly', () => {
        let error = UserValidator.validateEmail(obj, 'email');
        expect(error).toBeUndefined();
        obj.email = '12';
        error = UserValidator.validateEmail(obj, 'email');
        expect(error).toBeDefined();
    });
});