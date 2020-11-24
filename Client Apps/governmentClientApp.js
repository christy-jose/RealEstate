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
			const userExists = await wallet.get('govUser');
			if (!userExists) {
				console.log('An identity for the user "govUser" does not exist in the wallet');
				console.log('Run the registerUser.js application before retrying');
				return;
			}

			// Create a new gateway for connecting to our peer node.
			const gateway = new Gateway();
			await gateway.connect(ccp, { wallet, identity: 'govUser', discovery: { enabled: true, asLocalhost: true } });

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

app.post('/api/getGaRequest', async function (req, res) {
    try {
        
		var type = 'RQGA';
		var status = 'Issued';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('getRqsByType', type, status);
        console.log(`Transaction has been evaluated, result is: ${result}`);
        res.status(200).json({response: result.toString()});
        /*
        console.log(`Transaction has been evaluated, result is: assets found`);
        res.status(200).json({response: 'Assets found'});
		//*/
    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        res.status(500).json({error: error});
        process.exit(1);
    }
});

app.post('/api/approveGaUser', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var status = 'True';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('changeGaStatusUser', requestId, status);
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

app.post('/api/approveGaAsset', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var status = 'True';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('changeGaStatusAsset', requestId, status);
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

app.post('/api/declineGaUser', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var status = 'false';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('changeGaStatusUser', requestId, status);
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

app.post('/api/declineGaAsset', async function (req, res) {
    try {
        
		var requestId = req.body.requestId;
		var status = 'false';
        //Evaluate the specified transaction.
        const result = await contract.submitTransaction('changeGaStatusAsset', requestId, status);
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

app.listen(8070, 'localhost');
console.log('Running on http://localhost:8070');
main();