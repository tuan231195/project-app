const helpers = require('./helpers');
const config = {
	src: helpers.client('src'),
	app: helpers.client('src', 'main'),
	vendor: helpers.client('src', 'vendor'),
	polyfill: 'babel-polyfill',
	build: helpers.client('dist'),
};

module.exports = config;