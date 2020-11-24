/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.realEstate;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Request {

	@Property()
	@JsonProperty("docType")
    private final String docType;
    
    @Property()
    private final String id;

    @Property()
    private final String type;

    @Property()
    private final String status;
    
    @Property()
    private final String issuer;
    
    @Property()
    private final String asset;
    
    @Property()
    private final String recipient;

    public String getId() {
        return id;
    }
	
    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

	public String getIssuer() {
        return issuer;
    }
    
    public String getAsset() {
        return asset;
    }

	public String getRecipient() {
        return recipient;
    }
    
    public Request(@JsonProperty("id") final String id, @JsonProperty("type") final String type, @JsonProperty("status") final String status,
             @JsonProperty("issuer") final String issuer, @JsonProperty("asset") final String asset, @JsonProperty("recipient") final String recipient) {
        this.docType = "request";
        this.id = id;
        this.type = type;
        this.status = status;
        this.issuer = issuer;
        this.asset = asset;
        this.recipient = recipient;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Request other = (Request) obj;

        return Objects.deepEquals(new String[] {getId(), getType(), getStatus(), getIssuer(), getAsset(), getRecipient()},
                new String[] {other.getId(), other.getType(), other.getStatus(), other.getIssuer(), other.getAsset(), other.getRecipient()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getStatus(), getIssuer(), getAsset(), getRecipient());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [Id=" + id + ", Type="
                + type + ", Status=" + status + ", Issuer=" + issuer + ", Asset=" + asset + ", Recipient=" + recipient + "]";
    }
}

