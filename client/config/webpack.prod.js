const commonConfig = require('./webpack.common');
const merge = require('webpack-merge');
const parts = require('./webpack.parts');
const glob = require('globby');
const config = require('./config');

const ENV = process.env.NODE_ENV = process.env.ENV = 'production';

const prodConfig = merge([
	{
		performance: {
			hints: 'warning', // 'error' or false are valid too
			maxEntrypointSize: 1000000, // in bytes
			maxAssetSize: 45000000 // in bytes
		}
	},
	parts.clean(config.build),
	parts.extractCSS({
		use: ['css-loader', parts.autoprefix(), 'sass-loader']
	}),
	parts.define({
		'process.env': {
			'ENV': JSON.stringify(ENV)
		}
	}),
	parts.minifyCSS({
		options: {
			discardComments: {
				removeAll: true,
				// Run cssnano in safe mode to avoid
				// potentially unsafe transformations.
				safe: true
			}
		}
	}),
	parts.purifyCSS({
		paths: glob.sync(`${config.app}/**/*.js`, { nodir: true }),
	}),
	parts.minifyJavaScript(),
	parts.ignoreErrors(),
]);

module.exports = merge([commonConfig, prodConfig]);