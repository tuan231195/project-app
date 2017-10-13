const webpack = require('webpack');
const merge = require('webpack-merge');
const helpers = require('./helpers');
const parts = require('./webpack.parts');
const ENV = process.env.NODE_ENV = process.env.ENV = 'test';

module.exports = merge([
	{
		devtool: 'inline-source-map',
		resolve: {
			extensions: ['.ts', '.tsx', '.js', '.scss'],
		},
	},
	parts.loadTypeScript(),
	parts.loadHTML(),
	parts.loadImages({
		loader: 'null-loader'
	}),
	parts.define({
		'process.env': {
			'ENV': JSON.stringify(ENV)
		}
	}),
	parts.loadFonts({
		loader: 'null-loader'
	}),
	parts.loadSass({
		use: 'null-loader'
	}),
]);

