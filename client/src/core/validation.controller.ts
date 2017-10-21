export class ValidationController {
    protected formFields: Map<string, object>;

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