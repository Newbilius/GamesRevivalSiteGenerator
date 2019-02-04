const webpack = require('webpack')

module.exports = {
  entry: './app.js',
  output: {
    path: __dirname + '/public/dist',
    filename: 'bundle.js'
  },
  externals: {
    jquery: 'jQuery'
  },
  resolve: {
    modules: [
      __dirname + "/public/js",
      "node_modules"
    ]
  },
  plugins: [
  new webpack.ProvidePlugin({
              $: "jquery",  
              jQuery: "jquery" 
          })
  ],
  module: {
    rules: [{
      test: /\.css$/,
      loaders: [
        'style-loader',
        'css-loader'
      ]
    }, {
    test: require.resolve('jquery'),
    use: [{
        loader: 'expose-loader',
        options: 'jQuery'
    },{
        loader: 'expose-loader',
        options: '$'
    }]
   }]
  }
}
