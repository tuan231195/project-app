// this file is only being used by karma
require('phantomjs-polyfill');

requireAll((require as any).context('./', true, /spec.ts$/));

function requireAll(r: any): any {
    r.keys().forEach(r);
}