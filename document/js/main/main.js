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
	if ((minute/15)%2 == 0) {

		// write value
		fs.appendFileSync(namefile, 'Max value : ' + maxValue +' || Min value : ' + minValue +' || money : ' + balanceUSDT + ' || coins : ' + balanceCoin, (err) => {
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
		flagBuy = true;
	  	flagSell = true;
		namefile = prefix + Math.round((new Date()).getTime()/1000) + '.txt';
	}

}, 60000);

var balanceUSDT = 0;
var balanceCoin = 0;
var nameCoin = 'ETH';
// get balance
setInterval(() => {
	poloniex.returnBalances((err, data) => {
	  if (!err) {
	  	balanceUSDT = data.USDT;
	  	balanceCoin = data[nameCoin];
	  } 
	});
}, 10000);

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
	  	if ((balanceUSDT > 2) && (currentValue - minValue >= (minValue * buyPercent))) {
	  		console.log('buy value : ' + currentValue + ' | min value : ' + minValue);
	  		buyValue = currentValue;
	  		flagBuy = false;;

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
	  	if (balanceCoin >= 0.005 && buyValue > 0) {
	  		console.log('sell value : ' + currentValue + ' | buy value : ' + buyValue);

	  		fs.appendFileSync(namefile, 'Sell value : ' + currentValue + '\r\n', (err) => {
		  		if(err) {
		  			poloniex.unSubscribe(channelSubcribe);
		  			poloniex.closeWebSocket();
		  			throw err;
		  		}
		  		console.log('Save');
		  	});

	  		var sellOrderVal = buyValue + (buyValue * sellPercent);

			poloniex.emit('orderSell', sellOrderVal);
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
	var m = this.balanceUSDT - (this.balanceUSDT * fee);
	this.balanceCoin = m/value;

	// order Buy
	poloniex.buy('USDT_' + nameCoin, value, this.balanceCoin, null, null, null, (err, data) => {
	  if (!err) console.log(data);
	  	else throw err;
	});
});

poloniex.on('executeSell', (value) => {
	console.log('executeSell');
	var m = this.balanceCoin * value;
	this.balanceUSDT = m - (m * fee);
});

poloniex.on('orderSell', (value) => {
	console.log('executeSell');
	var m = this.balanceCoin * value;
	this.balanceUSDT = m - (m * fee);

	// order Buy
	poloniex.sell('USDT_' + nameCoin, value, this.balanceCoin, null, null, null, (err, data) => {
	  if (!err) console.log(data);
	  	else throw err;
	});
});
