import {Error} from '../../core/model/Error';
import isNumber = require('lodash/isNumber');
import isInteger = require('lodash/isInteger');
import {ucFirst} from '../../core/utils/strings';

export class UserValidator {
    public static validateInteger(object, field: string, getFields?: any): Error {
        let error = null;
        if (!isInteger(object[field])) {
            error = new Error(ucFirst(field) + ' must be an integer');
        }
        if (getFields) {
            const fields = getFields();
            const f = fields.get(field);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }

    public static validateNumber(object, field: string, getFields?): Error {
        let error = null;
        if (!isNumber(object[field])) {
            error = new Error(ucFirst(field) + ' must be a number');
        }
        if (getFields) {
            const fields = getFields();
            const f = fields.get(field);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }

    public static validateEmail(object, field: string, getFields?) {
        let error = null;
        const mailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (!object[field].match(mailFormat)) {
            error = new Error('Not a valid email address');
        }
        if (getFields) {
            const fields = getFields();
            const f = fields.get(field);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }

    public static validatePassword(object, field: string, {minLength = 4, maxLength = Infinity} = {}, getFields?) {
        let error = null;
        const password = object[field].trim();
        if (password.length < minLength || password > maxLength) {
            if (maxLength !== Infinity) {
                error = new Error(`Password must be between ${minLength} and ${maxLength} characters`);
            } else {
                error = new Error(`Password must not be less than ${minLength} characters`);
            }
        } else {
            const regex = new RegExp('^(?=.*[\\W])(?=[a-z0-9])[\\w\\W]{' + minLength + ',' +
                ((maxLength !== Infinity) ? maxLength : '') + '}$');
            if (!regex.test(password)) {
                error = new Error('Password must have at least one speacial characters.');
            }
        }
        if (getFields) {
            const fields = getFields();
            const f = fields.get(field);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }

    public static validateRequired(object, field: string, getFields?) {
        let error = null;
        if (object[field] === null || object[field] === undefined || object[field].trim().length === 0) {
            error = new Error('Field ' + field + ' is required');
        }
        if (getFields) {
            const fields = getFields();
            const f = fields.get(field);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }

    public static validateConfirm(object, field: string, confirmField: string, getFields?) {
        let error = null;
        if (object[field].trim() !== object[confirmField].trim()) {
            error =  new Error(ucFirst(field) + ' does not match');
        }
        if (getFields) {
            const fields = getFields();
            let f = fields.get(field);
            f.valid = !error;

            f = fields.get(confirmField);
            f.error = error ? error.title : null;
            f.valid = !error;
        }
        return error;
    }
}