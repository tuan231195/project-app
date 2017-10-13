let path = require('path');
let _clientRoot = path.resolve(__dirname, '..');

function client(args) {
	args = Array.prototype.slice.call(arguments, 0);
	return path.join.apply(path, [_clientRoot].concat(args));
}


exports.client = client;