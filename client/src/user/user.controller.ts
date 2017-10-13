export class UserController {
    private value1: any;

    constructor(value1, moduleValue1) {
        this.value1 = value1;
    }
}

UserController.$inject = ['value1', 'moduleValue1'];