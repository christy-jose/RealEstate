/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.realEstate;

import java.util.ArrayList;
import java.util.List;
import java.lang.Long;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;


@Contract(
        name = "RealEstate",
        info = @Info(
                title = "RealEstate contract",
                description = "The hyperlegendary real estate contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "christyjoseknb@gmail.com",
                        name = "Christy Jose",
                        url = "")))
@Default
public final class RealEstate implements ContractInterface {

    private final Genson genson = new Genson();
    
    private final String UserNumKey = "NUSR";
    private final String AssetNumKey = "NAST";
    private final String RequestNumKey = "NREQ";

    private enum RealEstateErrors {
        NOT_FOUND,
        ALREADY_EXISTS,
        INVALID_TRANSACTION
    }

	
	@Transaction()
	public long getNextNum(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String numberState = stub.getStringState(key);

        if (numberState.isEmpty()) {
            String errorMessage = String.format("%s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.NOT_FOUND.toString());
        }

        long num = Long.parseLong(numberState);

        return num;
    }
	
	@Transaction()
	public User queryUser(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String userState = stub.getStringState(key);

        if (userState.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.NOT_FOUND.toString());
        }

        User user = genson.deserialize(userState, User.class);

        return user;
    }
    
    @Transaction()
	public Asset queryAsset(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String assetState = stub.getStringState(key);

        if (assetState.isEmpty()) {
            String errorMessage = String.format("Asset %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.NOT_FOUND.toString());
        }

        Asset asset = genson.deserialize(assetState, Asset.class);

        return asset;
    }
    
    @Transaction()
    public Asset[] getAssetsOfUser(final Context ctx, final String userid) {
        ChaincodeStub stub = ctx.getStub();
        String queryString = String.format("{\"selector\":{\"docType\":\"asset\",\"owner\":\"%s\"}, \"use_index\":[\"AssetIndexDoc1\", \"AssetIndexOwner\"]}", userid);
        //String assetState = stub.getStringState(key);
		
        List<Asset> assets = new ArrayList<Asset>();

        QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString);

        for (KeyValue result: results) {
            Asset asset = genson.deserialize(result.getStringValue(), Asset.class);
            assets.add(asset);
        }

        Asset[] response = assets.toArray(new Asset[assets.size()]);

        return response;

    }
    
    @Transaction()
    public Asset[] getAssetsByStatus(final Context ctx, final String status) {
        ChaincodeStub stub = ctx.getStub();
        String queryString = String.format("{\"selector\":{\"docType\":\"asset\",\"status\":\"%s\"}, \"use_index\":[\"AssetIndexDoc2\", \"AssetIndexStatus\"]}", status);
        //String assetState = stub.getStringState(key);
		
        List<Asset> assets = new ArrayList<Asset>();

        QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString);

        for (KeyValue result: results) {
            Asset asset = genson.deserialize(result.getStringValue(), Asset.class);
            assets.add(asset);
        }

        Asset[] response = assets.toArray(new Asset[assets.size()]);

        return response;

    }
    
    @Transaction()
	public Request queryRequest(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String requestState = stub.getStringState(key);

        if (requestState.isEmpty()) {
            String errorMessage = String.format("Request %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.NOT_FOUND.toString());
        }

        Request request = genson.deserialize(requestState, Request.class);

        return request;
    }
    
    @Transaction()
    public Request[] getRqsOfUser(final Context ctx, final String type,final String userid) {
        ChaincodeStub stub = ctx.getStub();
        String queryString = String.format("{\"selector\":{\"docType\":\"request\",\"type\":\"%s\",\"issuer\":\"%s\"}, \"use_index\":[\"RequestIndexDoc1\", \"RequestIndexIssuer\"]}", type, userid );
        //String assetState = stub.getStringState(key);
		
        List<Request> requests = new ArrayList<Request>();

        QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString);

        for (KeyValue result: results) {
            Request request = genson.deserialize(result.getStringValue(), Request.class);
            requests.add(request);
        }

        Request[] response = requests.toArray(new Request[requests.size()]);

        return response;

    }
    
    @Transaction()
    public Request[] getRqsOfRecipient(final Context ctx, final String type,final String userid) {
        ChaincodeStub stub = ctx.getStub();
        String queryString = String.format("{\"selector\":{\"docType\":\"request\",\"type\":\"%s\",\"recipient\":\"%s\"}, \"use_index\":[\"RequestIndexDoc3\", \"RequestIndexRecipient\"]}", type, userid );
        //String assetState = stub.getStringState(key);
		
        List<Request> requests = new ArrayList<Request>();

        QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString);

        for (KeyValue result: results) {
            Request request = genson.deserialize(result.getStringValue(), Request.class);
            requests.add(request);
        }

        Request[] response = requests.toArray(new Request[requests.size()]);

        return response;

    }
    
    @Transaction()
    public Request[] getRqsByType(final Context ctx, final String type,final String status) {
        ChaincodeStub stub = ctx.getStub();
        String queryString = String.format("{\"selector\":{\"docType\":\"request\",\"type\":\"%s\",\"status\":\"%s\"}, \"use_index\":[\"RequestIndexDoc4\", \"RequestIndexStatus\"]}", type, status );
        //String assetState = stub.getStringState(key);
		
        List<Request> requests = new ArrayList<Request>();

        QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString);

        for (KeyValue result: results) {
            Request request = genson.deserialize(result.getStringValue(), Request.class);
            requests.add(request);
        }

        Request[] response = requests.toArray(new Request[requests.size()]);

        return response;

    }
  
	@Transaction()
	public void initLedger2(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String[] userData = {
                "{ \"name\": \"TestUser1\", \"phNumber\": \"9999999999\", \"id\": \"USR1\", \"fa\": \"True\", \"ga\": \"True\" }",
                "{ \"name\": \"TestUser2\", \"phNumber\": \"9999999999\", \"id\": \"USR2\", \"fa\": \"True\", \"ga\": \"True\" }",
                
        };

        for (int i = 0; i < userData.length; i++) {
            String key = String.format("USR%d", (i+1));

            User user = genson.deserialize(userData[i], User.class);
            String userState = genson.serialize(user);
            stub.putStringState(key, userState);
        }
        stub.putStringState(UserNumKey,"3");
        stub.putStringState(AssetNumKey,"1");
        stub.putStringState(RequestNumKey,"1");
    }
 
     @Transaction()
	public long putNextNum(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String numberState = stub.getStringState(key);

        if (numberState.isEmpty()) {
            String errorMessage = String.format("%s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.NOT_FOUND.toString());
        }

        long num = Long.parseLong(numberState);
		num = num + 1;
		stub.putStringState(key,Long.toString(num));
        return num;
    }
    
    @Transaction()
    public User addUser(final Context ctx, final String name, final String phNumber) {
        ChaincodeStub stub = ctx.getStub();
		
		long num = getNextNum(ctx,UserNumKey);
		final String key = "USR" + Long.toString(num);
        
        String userState = stub.getStringState(key);
        if (!userState.isEmpty()) {
            String errorMessage = String.format("User %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.ALREADY_EXISTS.toString());
        }

        User user = new User(key, name, phNumber, "False", "False");
        
        stub.putState(key, genson.serializeBytes(user));
		num = putNextNum(ctx,UserNumKey);
		
        return user;
    }
    
    @Transaction()
    public Asset addAsset(final Context ctx, final String type,
            final String price, final String owner) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();
		
		long num = getNextNum(ctx,AssetNumKey);
		final String key = "AST" + Long.toString(num);
		
		User user  = queryUser(ctx,owner);
		
        String assetState = stub.getStringState(key);
        if (!assetState.isEmpty()) {
            String errorMessage = String.format("Asset %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.ALREADY_EXISTS.toString());
        }
        
        if (!user.getGa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by government", owner);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        Asset asset = new Asset(key, type, price, owner, "False", "Registered", "N/A");
        stub.putState(key, genson.serializeBytes(asset));
        num = putNextNum(ctx,AssetNumKey);
        //try {
		//String indexName = "assetOwner";
		//String[] attributes = {asset.getOwner()};
		//String n = " ";
		//stub.putState(stub.createCompositeKey(indexName,attributes).toString(),n.getBytes());
		//}
		//catch(Exception e) {
		//	System.out.println(e.getMessage());
		//}	
		
		System.out.println("Added Asset");
        return asset;
    }
    
    @Transaction()
    private Request addRequest(final Context ctx, final String key, final String type, final String status,
             final String issuer, final String asset, final String recipient) {
        ChaincodeStub stub = ctx.getStub();

        String requestState = stub.getStringState(key);
        if (!requestState.isEmpty()) {
            String errorMessage = String.format("Request %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.ALREADY_EXISTS.toString());
        }

        Request request = new Request(key, type, status, issuer, asset, recipient);
        requestState = genson.serialize(request);
        stub.putStringState(key, requestState);

        return request;
    }
    
    @Transaction()
    public Request RequestFa(final Context ctx,final String userid) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();
    
        //final String key = "RQ001";    
        long num = getNextNum(ctx,RequestNumKey);
        final String key = "REQ" + Long.toString(num);
        
        User user  = queryUser(ctx,userid);
        
        if (!user.getGa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by government", userid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        Request request = addRequest(ctx,key,"RQFA","Issued",userid,"N/A","N/A");
        num = putNextNum(ctx,RequestNumKey);
        
        return request;
    }

	@Transaction()
	public Request RequestGaUser(final Context ctx,final String userid) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();
       
        //final String key = "RQ002";    
        long num = getNextNum(ctx,RequestNumKey);
        final String key = "REQ" + Long.toString(num);
		
		Request request = addRequest(ctx,key,"RQGA","Issued",userid,"N/A","N/A");
        num = putNextNum(ctx,RequestNumKey);
        
        return request;
    }
    
    @Transaction()
	public Request RequestGaAsset(final Context ctx,final String userid, final String assetid) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();

        //final String key = "RQ003";
        long num = getNextNum(ctx,RequestNumKey);
        final String key = "REQ" + Long.toString(num);
        
        Asset asset = queryAsset(ctx,assetid);
        User user  = queryUser(ctx,userid);
        
        if (!asset.getOwner().equals(user.getId())) {
			String errorMessage = String.format("Asset %s does not belong to user %s", assetid, userid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
		Request request = addRequest(ctx,key,"RQGA","Issued",userid,assetid,"N/A");
		num = putNextNum(ctx,RequestNumKey);
		
        return request;
    }
    
    @Transaction()
	public Request RequestBuy(final Context ctx,final String userid, final String assetid) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();
        
        //final String key = "RQ004";
        long num = getNextNum(ctx,RequestNumKey);
        final String key = "REQ" + Long.toString(num);
        
        Asset asset = queryAsset(ctx,assetid);
        User user  = queryUser(ctx,userid);
        
        if (asset.getOwner().equals(user.getId())) {
			String errorMessage = String.format("Asset %s belongs to user %s", assetid, userid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!user.getFa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by financial Org", userid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if(!asset.getStatus().equals("Listed")) {
			String errorMessage = String.format("Asset %s is not avaliable for trading.", assetid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
			
		Request request = addRequest(ctx,key,"RQBUY","Issued",userid,assetid,asset.getOwner());
        num = putNextNum(ctx,RequestNumKey);
        
        return request;
    }

	
	@Transaction()
    public User[] queryAllUsers(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        final String startKey = "USR1";
        final String endKey = "USR99";
        List<User> users = new ArrayList<User>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);

        for (KeyValue result: results) {
            User user = genson.deserialize(result.getStringValue(), User.class);
            users.add(user);
        }

        User[] response = users.toArray(new User[users.size()]);

        return response;
    }

    
    @Transaction()
    public User changeFaStatus(final Context ctx, final String requestid, final String newStatus) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();

		Request request = queryRequest(ctx,requestid);
        User user = queryUser(ctx,request.getIssuer());
        
        User newUser;
        if(!user.getFa().equals(newStatus)) {
			newUser = new User(user.getId(),user.getName(),newStatus,user.getGa(),user.getPhNumber()); 
			stub.putState(user.getId(), genson.serializeBytes(newUser));
			return newUser;
		}	

        return user;
    }
    
    @Transaction()
    public User changeGaStatusUser(final Context ctx, final String requestid, final String newStatus) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();
		
		Request request = queryRequest(ctx,requestid);
        User user = queryUser(ctx,request.getIssuer());
        
        User newUser;
        if(!user.getGa().equals(newStatus)) {
			newUser = new User(user.getId(),user.getName(),user.getFa(),newStatus,user.getPhNumber()); 
			stub.putState(user.getId(), genson.serializeBytes(newUser));
			return newUser;
		}	

        return user;
    }
    
    @Transaction()
    public Asset changeGaStatusAsset(final Context ctx, final String requestid, final String newStatus) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();

        Request request = queryRequest(ctx,requestid);
        Asset asset = queryAsset(ctx,request.getAsset());
        User user = queryUser(ctx,request.getIssuer());
        
        if (!asset.getOwner().equals(user.getId())) {
			String errorMessage = String.format("Asset %s does not belong to user %s", asset.getId(), user.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        Asset newAsset;
        if(!asset.getGa().equals(newStatus)) {
			newAsset = new Asset(asset.getId(),asset.getType(),asset.getPrice(),asset.getOwner(),newStatus,asset.getStatus(),asset.getNominee()); 
			stub.putState(asset.getId(), genson.serializeBytes(newAsset));
			return newAsset;
		}	

        return asset;
    }
    
    @Transaction()
    public Asset changeStatusAsset(final Context ctx, final String assetid, final String userid, final String newStatus) throws ChaincodeException {
        ChaincodeStub stub = ctx.getStub();

        Asset asset = queryAsset(ctx,assetid);		
        User user = queryUser(ctx,userid);
        
        if (!asset.getOwner().equals(user.getId())) {
			String errorMessage = String.format("Asset %s does not belong to user %s", assetid, userid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!asset.getGa().equals("True")) {
			String errorMessage = String.format("Asset %s has not been approved by government", assetid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
		
        Asset newAsset;
        if(!asset.getStatus().equals(newStatus)) {
			newAsset = new Asset(asset.getId(),asset.getType(),asset.getPrice(),asset.getOwner(),asset.getGa(),newStatus,asset.getNominee()); 
			stub.putState(asset.getId(), genson.serializeBytes(newAsset));
			return newAsset;
		}	

        return asset;
    }
    
    @Transaction()
    public Asset ApproveBuySeller(final Context ctx, final String requestid, String userid) throws ChaincodeException {
		ChaincodeStub stub = ctx.getStub();
		
		Request request = queryRequest(ctx,requestid);
		Asset asset = queryAsset(ctx,request.getAsset());
		User buyer = queryUser(ctx,request.getIssuer());
		User seller = queryUser(ctx,request.getRecipient());
		User owner = queryUser(ctx,asset.getOwner());
		
        if (!(userid.equals(seller.getId()) && userid.equals(owner.getId()))) {
			String errorMessage = String.format("User %s is not authorised to approve request %s", userid, requestid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!buyer.getFa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by financial Org", buyer.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!asset.getStatus().equals("Listed")) {
			String errorMessage = String.format("Asset %s is not approved for trading.", asset.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
		Request newRequest  = new Request(request.getId(),request.getType(),"SellerApproved",request.getIssuer(),request.getAsset(),request.getRecipient());
		Asset newAsset = new Asset(asset.getId(),asset.getType(),asset.getPrice(),asset.getOwner(),asset.getGa(),asset.getStatus(),buyer.getId());
		stub.putState(request.getId(), genson.serializeBytes(newRequest));
		stub.putState(asset.getId(), genson.serializeBytes(newAsset));
		
		return newAsset;
				
	}
	
	@Transaction()
    public Request ApproveBuyBuyer(final Context ctx, final String requestid, String userid) throws ChaincodeException {
		ChaincodeStub stub = ctx.getStub();
		
		Request request = queryRequest(ctx,requestid);
		Asset asset = queryAsset(ctx,request.getAsset());
		User buyer = queryUser(ctx,request.getIssuer());
		User seller = queryUser(ctx,request.getRecipient());
		User owner = queryUser(ctx,asset.getOwner());
		
        if (!(userid.equals(buyer.getId()) && userid.equals(asset.getNominee()))) {
			String errorMessage = String.format("User %s is not authorised to approve request %s", userid, requestid);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!buyer.getFa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by financial Org", buyer.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!asset.getStatus().equals("Listed")) {
			String errorMessage = String.format("Asset %s is not avaliable for trading.", asset.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!(seller.getId().equals(owner.getId()))) {
			String errorMessage = String.format("Sale of Asset %s is not authorised by owner", asset.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (request.getStatus().equals("SellerApproved")) {
			Request newRequest  = new Request(request.getId(),request.getType(),"BuyerApproved",request.getIssuer(),request.getAsset(),request.getRecipient());
			stub.putState(request.getId(), genson.serializeBytes(newRequest));
			return newRequest;
		}
		
		return request;
				
	}
	
	@Transaction()
    public Asset ApproveBuyFinincal(final Context ctx, final String requestid) throws ChaincodeException {
		ChaincodeStub stub = ctx.getStub();
		
		Request request = queryRequest(ctx,requestid);
		Asset asset = queryAsset(ctx,request.getAsset());
		User buyer = queryUser(ctx,request.getIssuer());
		User seller = queryUser(ctx,request.getRecipient());
		User owner = queryUser(ctx,asset.getOwner());
		
		if (!buyer.getFa().equals("True")) {
			String errorMessage = String.format("User %s has not been approved by financial Org", buyer.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!asset.getStatus().equals("Listed")) {
			String errorMessage = String.format("Asset %s is not avaliable for trading.", asset.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!(seller.getId().equals(owner.getId()))) {
			String errorMessage = String.format("Sale of Asset %s is not authorised by owner", asset.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
        if (!(buyer.getId().equals(asset.getNominee()))) {
			String errorMessage = String.format("Asset %s is not approved to be sold to user %s", asset.getId(), buyer.getId());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RealEstateErrors.INVALID_TRANSACTION.toString());
        }
        
		Request newRequest  = new Request(request.getId(),request.getType(),"FinicialApproved",request.getIssuer(),request.getAsset(),request.getRecipient());
		Asset newAsset = new Asset(asset.getId(),asset.getType(),asset.getPrice(),buyer.getId(),asset.getGa(),"Registered","N/A");
		stub.putState(request.getId(), genson.serializeBytes(newRequest));
		stub.putState(asset.getId(), genson.serializeBytes(newAsset));
		
		return newAsset;
	}	
	
	@Transaction()
    public Request DeclineBuyFinincal(final Context ctx, final String requestid) throws ChaincodeException {
		ChaincodeStub stub = ctx.getStub();
		
		Request request = queryRequest(ctx,requestid);
		Asset asset = queryAsset(ctx,request.getAsset());
		User buyer = queryUser(ctx,request.getIssuer());
		User seller = queryUser(ctx,request.getRecipient());
		User owner = queryUser(ctx,asset.getOwner());
        
		Request newRequest  = new Request(request.getId(),request.getType(),"FinicialDeclined",request.getIssuer(),request.getAsset(),request.getRecipient());
		stub.putState(request.getId(), genson.serializeBytes(newRequest));
		
		
		return newRequest;
	}			
}
