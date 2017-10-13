const commonConfig = require('./webpack.common');
const parts = require('./webpack.parts.js');
const merge = require('webpack-merge');
const ENV = process.env.NODE_ENV = process.env.ENV = 'development';

const devConfig = merge([
	parts.generateSourceMaps({
		type: 'cheap-module-eval-source-map'
	}),
	parts.define({
		'process.env': {
			'ENV': JSON.stringify(ENV),
		}
	}),
	parts.loadCSS({
		use: ['style-loader', 'css-loader']
	}),
	parts.loadSass({
		use: [{
			loader: 'style-loader' // creates style nodes from JS strings
		}, {
			loader: 'css-loader' // translates CSS into CommonJS
		}, {
			loader: 'sass-loader' // compiles Sass to CSS
		}]
	}),
	parts.devServer({
		host: process.env.HOST, // Defaults to `localhost`
		port: process.env.PORT // Defaults to 8080
	})
]);

module.exports = merge([commonConfig, devConfig]);