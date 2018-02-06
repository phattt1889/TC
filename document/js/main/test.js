const fs = require('fs');

'use strict';



/*var start = Math.round((new Date("2018-02-06T02:30:00Z")).getTime()/1000);

var filePath = __dirname + '/data/' + start + '.txt';

console.log(filePath);

fs.appendFile(filePath, 'Hello content!', (err) => {
  if (err) throw err;
  console.log('Saved!');
});*/


var prefix = '/data/'
var namefile = prefix + Math.round((new Date()).getTime()/1000) + '.txt';

setInterval(() => {
	console.log('check create file');

	let date = new Date();

	// check minute
	let minute = date.getMinutes();
	if (minute == 28 || minute == 1) {
		console.log('in');
		namefile = prefix + Math.round((new Date()).getTime()/1000) + '.txt';
	}

}, 2000);