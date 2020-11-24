var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());

// Setting for Hyperledger Fabric
const { Wallets, FileSystemWallet, Gateway } = require('fabric-network');
const path = require('path');
const fs = require('fs');

///*
const ccpPath = path.resolve(__dirname, '..', '..', 'test-network', 'organizations', 'peerOrganizations', 'org1.example.com', 'connection-org1.json');
const ccp = JSON.parse(fs.readFileSync(ccpPath, 'utf8'));
//*/

var contract = null;

async function main() {
	///*
	try {
		// Create a new file system based wallet for managing identities.
			const walletPath = path.join(process.cwd(), 'wallet');
			const wallet = await Wallets.newFileSystemWallet(walletPath);
			console.log(`Wallet path: ${walletPath}`);

			// Check to see if we've already enrolled the user.
			const userExists = await wallet.get('USR2');
			if (!userExists) {
				console.log('An identity for the user "USR2" does not exist in the wallet');
				console.log('Run the registerUser.js application before retrying');
				return;
			}

			// Create a new gateway for connecting to our peer node.
			const gateway = new Gateway();
			await gateway.connect(ccp, { wallet, identity: 'USR2', discovery: { enabled: true, asLocalhost: true } });

			// Get the network (channel) our contract is deployed to.
			const network = await gateway.getNetwork('mychannel');

			// Get the contract from the network.
			contract = network.getContract('realestate');
		
	}
	catch (error) {
		 console.error(`Failed to connect to smart Contract: ${error}`);
		 process.exit(1);
	}	
	//*/
}

app.post('/api/queryUser', async function (req, res) {
    try {
        
		var userId = 'USR2';
		console.log(userId);
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('queryUser',userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response : result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: user found`);
        res.status(200).json({name : 'Test User 1', phNumber : '9999999999', id : 'TUSR2', fa : 'false', ga : 'false'});
		//*/
	
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/queryAsset', async function (req, res) {
    try {
        
		var assetId = req.body.assetId;
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('queryAsset',assetId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response : result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: asset found`);
        res.status(200).json({owner : 'TUSR1', price : '10000', nominee : 'NA', ga : 'false', id : 'AS1', type : 'Villa', status : 'Registered'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getAssetsOfUser', async function (req, res) {
    try {
        
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getAssetsOfUser',userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response : result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({rowner : 'TUSR1', price : '10000', nominee : 'NA', ga : 'false', id : 'AS1', type : 'Villa', status : 'Registered'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getListedAssets', async function (req, res) {
    try {
        
		var status = 'Listed';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getAssetsByStatus',status);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/addAsset', async function (req, res) {
    try {
		var type = req.body.type;
		var price = req.body.price;
		var owner = 'USR2';
        // Submit the specified transaction.
        const result = await contract.submitTransaction('addAsset',type,price,owner);
		//const result = await contract.evaluateTransaction('getAssetsOfUser',status);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log('Transaction has been submitted');
        res.send('Transaction has been submitted');
        //*/
        // Disconnect from the gateway.
        // await gateway.disconnect();

    } catch (error) {
        console.error(`Failed to submit transaction: ${error}`);
        //process.exit(1);
    }
});

app.post('/api/requestGaAsset', async function (req, res) {
    try {
        
		var assetId = req.body.assetId;
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('RequestGaAsset',userId, assetId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/listAsset', async function (req, res) {
    try {
        
		var assetId = req.body.assetId;
		var userId = 'USR2';
		var status = 'Listed';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('changeStatusAsset',assetId, userId, status);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/requestBuy', async function (req, res) {
    try {
        
		var assetId = req.body.assetId;
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('RequestBuy', userId, assetId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/approveBuyOffer', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('ApproveBuySeller', requestId, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getBuyOffers', async function (req, res) {
    try {
        
		var type = 'RQBUY';
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getRqsOfRecipient', type, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getBuyRequest', async function (req, res) {
    try {
        
		var type = 'RQBUY';
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getRqsOfUser', type, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getGaRequest', async function (req, res) {
    try {
        
		var type = 'RQGA';
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getRqsOfUser', type, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/getFaRequest', async function (req, res) {
    try {
        
		var type = 'RQFA';
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getRqsOfUser', type, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});

app.post('/api/confirmBuyRequest', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var userId = 'USR2';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('ApproveBuyBuyer', requestId, userId);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        //process.exit(1);
    }
});


app.listen(8090, 'localhost');
console.log('Running on http://localhost:8090');
main();
