export class Error {
    public title: string;
    public desc: string;
    public errorCode: string;

    constructor(title: string, desc?: string, errorCode?: string) {
        this.title = title;
        this.desc = desc;
        this.errorCode = errorCode;
    }
}