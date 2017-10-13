var webpackConfig = require('./webpack.test');
require('phantomjs-polyfill');

module.exports = function (config) {
	const _config = {
		basePath: '',

		frameworks: ['jasmine'],
		files: [
			'../node_modules/phantomjs-polyfill/bind-polyfill.js',
			'../src/test.ts'
		],
		preprocessors: {
			// add webpack as preprocessor
			'../src/test.ts': ['webpack'],
		},
		webpack: webpackConfig,

		webpackMiddleware: {
			stats: 'errors-only'
		},

		webpackServer: {
			noInfo: true
		},

		reporters: ['progress'],
		port: 9876,
		colors: true,
		logLevel: config.LOG_INFO,
		browsers: ['PhantomJS'],
	};

	config.set(_config);
};
