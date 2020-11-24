/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.realEstate;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Asset {

    @Property()
    @JsonProperty("docType")
    private final String docType;
    
    @Property()
    private final String id;

    @Property()
    private final String type;

    @Property()
    private final String price;

    @Property()
    private final String owner;
    
    @Property()
    private final String ga;
    
    @Property()
    private final String status;
    
    @Property()
    private final String nominee;

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public String getGa() {
        return ga;
    }
    
    public String getId() {
        return id;
    }

	public String getStatus() {
        return status;
    }
    
    public String getNominee() {
        return nominee;
    }
    
    public Asset(@JsonProperty("id") final String id, @JsonProperty("type") final String type, @JsonProperty("price") final String price,
            @JsonProperty("owner") final String owner,@JsonProperty("ga") final String ga, @JsonProperty("status") final String status, @JsonProperty("nominee") final String nominee) {
        
        this.docType = "asset";
        this.type = type;
        this.price = price;
        this.id = id;
        this.ga = ga;
        this.owner = owner;
        this.status = status;
        this.nominee = nominee;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Asset other = (Asset) obj;

        return Objects.deepEquals(new String[] {getId(), getType(), getPrice(), getOwner(), getGa(), getStatus(), getNominee()},
                new String[] {other.getId(), other.getType(), other.getPrice(), other.getOwner(), other.getGa(), other.getStatus(), other.getNominee()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getPrice(), getOwner(), getGa(), getStatus(), getNominee());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [Id=" + id + ", Type="
                + type + ", Price=" + price + ", Owner=" + owner + ", GA=" + ga + ", Status=" + status + ", Nominee=" + nominee + "]";
    }
}

