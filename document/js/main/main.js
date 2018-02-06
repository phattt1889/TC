const Poloniex = require('poloniex-api-node');
const SocksProxyAgent = require('socks-proxy-agent');
const apiKey = '2BAYUQJM-ZL9TLTSS-V6MW19JF-LQEIYZ7Q';
const secretKey = '3e62786029dba31da1a4659008e6df3b973a16aef1b5e8d835de700d0c3c7fd53c1aa8d33750b1bf89cd88add436200af31d6d0c848c541e431565dc3e91c6f3';

// read write file
const fs = require('fs');

// https://www.socks-proxy.net/
const proxyServer = 'socks://47.90.102.176:38111';

let poloniex = new Poloniex(apiKey, secretKey, { nonce: () => new Date().time(), agent: new SocksProxyAgent(proxyServer)});

var start = Math.round((new Date("2018-02-06T02:30:00Z")).getTime()/1000);
var end = Math.round((new Date("2018-02-06T03:00:00Z")).getTime()/1000);

var prefix = __dirname + '/data/'
var namefile = prefix + Math.round((new Date()).getTime()/1000) + '.txt';

var maxValue = 0;
var currentValue = 0;
var minValue = 0;
var buyValue = 0;
const buyPercent = 0.009;
const sellPercent = 0.01;
var money = 1000;
var coins = 0;
const fee = 0.03;
var flagSell = true;
var flagBuy = true;

setInterval(() => {
	console.log('check create file');

	let date = new Date();

	// check minute
	let minute = date.getMinutes();
	if (minute == 30 || minute == 1) {

		// write value
		fs.appendFileSync(namefile, 'Max value : ' + maxValue +' || Min value : ' + minValue +' || money : ' + money + ' || coins : ' + coins, (err) => {
	  		if(err) {
	  			poloniex.unSubscribe(channelSubcribe);
	  			poloniex.closeWebSocket();
	  			throw err;
	  		}
	  		console.log('Save');
	  	});

		maxValue = 0;
		minValue = 0;
		currentValue = 0;
		namefile = prefix + Math.round((new Date()).getTime()/1000) + '.txt';
	}

}, 60000);

/*poloniex.returnTradeHistory('USDT_ETH', start, end, null, function (err, data) {
  if (err) {
    console.log(err.message);
  } else {
    console.log(data[0].globalTradeID);
  }
});*/
poloniex.on('open', () => {
  console.log('WebSocket connection is open.');
});

poloniex.on('error', (error) => {
  console.log(error);
});

poloniex.on('close', (error) => {
  console.log('WebSocket close');
});

var channelSubcribe = 'USDT_ETH';

poloniex.subscribe(channelSubcribe);
poloniex.on('message', (channelName, data) => {
  if (channelName === channelSubcribe) {

  	data.forEach(function(element) {
	  
	  // check date write
	  if (element.type == 'newTrade') {
	  	console.log('in new trade');
	  	console.log(element.data);

	  	if (currentValue == 0) {
	  		maxValue = element.data.rate;
			minValue = element.data.rate;
	  	}

	  	currentValue = element.data.rate;

	  	// get max value and min value
	  	if (currentValue > maxValue) {
	  		maxValue = currentValue;
	  	}

	  	if (currentValue < minValue) {
	  		minValue = currentValue;
	  	}

	  	// check buy
	  	if (flagBuy && (currentValue - minValue >= (minValue * buyPercent))) {
	  		console.log('buy value : ' + currentValue + ' | min value : ' + minValue);
	  		buyValue = currentValue;
	  		flagBuy = false;
	  		flagSell = true;

	  		fs.appendFileSync(namefile, 'Buy value : ' + buyValue + '\r\n', (err) => {
		  		if(err) {
		  			poloniex.unSubscribe(channelSubcribe);
		  			poloniex.closeWebSocket();
		  			throw err;
		  		}
		  		console.log('Save');
		  	});

	  		// reset value
	  		maxValue = 0;
			minValue = 0;
			currentValue = 0;
			poloniex.emit('executeBuy', buyValue);
	  	}

	  	// check sell
	  	console.log('buyValue : ' + buyValue + ' | currentValue - buyValue : ' + (currentValue - buyValue) + " || (buyValue * sellPercent) : " + (buyValue * sellPercent));
	  	if (flagSell && buyValue != 0 && ((currentValue - buyValue) >= (buyValue * sellPercent))) {
	  		console.log('sell value : ' + currentValue + ' | buy value : ' + buyValue);
	  		buyValue = 0;
	  		flagBuy = true;
	  		flagSell = false;

	  		fs.appendFileSync(namefile, 'Sell value : ' + currentValue + '\r\n', (err) => {
		  		if(err) {
		  			poloniex.unSubscribe(channelSubcribe);
		  			poloniex.closeWebSocket();
		  			throw err;
		  		}
		  		console.log('Save');
		  	});

			poloniex.emit('executeSell', currentValue);
	  	}

	  	fs.appendFileSync(namefile, JSON.stringify(element.data) + '\r\n', (err) => {
	  		if(err) {
	  			poloniex.unSubscribe(channelSubcribe);
	  			poloniex.closeWebSocket();
	  			throw err;
	  		}
	  		console.log('Save');
	  	});
	  }
	});
  }
});

// open websocket
poloniex.openWebSocket({ version: 2 });

poloniex.on('executeBuy', (value) => {
	console.log('executeBuy');

	// number coins has bought
	// get coin by when - fee
	var m = this.money - (this.money * fee);
	this.coins = m/value;

});

poloniex.on('executeSell', (value) => {
	console.log('executeSell');
	var m = this.coins * value;
	this.money = m - (m * fee);
});
