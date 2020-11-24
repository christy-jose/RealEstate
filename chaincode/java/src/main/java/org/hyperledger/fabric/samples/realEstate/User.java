/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.realEstate;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class User {

    @Property()
    @JsonProperty("docType")
    private final String docType;
    
    @Property()
    private final String name;

    @Property()
    private final String phNumber;

    @Property()
    private final String id;

    @Property()
    private final String fa;
    
    @Property()
    private final String ga;

    public String getName() {
        return name;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public String getId() {
        return id;
    }

    public String getFa() {
        return fa;
    }
	
	public String getGa() {
        return ga;
    }

    public User(@JsonProperty("id") final String id, @JsonProperty("name") final String name, 
    @JsonProperty("fa") final String fa, @JsonProperty("ga") final String ga, @JsonProperty("phNumber") final String phNumber) {
        this.docType = "user";
        this.name = name;
        this.phNumber = phNumber;
        this.id = id;
        this.fa = fa;
        this.ga = ga;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        User other = (User) obj;

        return Objects.deepEquals(new String[] {getName(), getPhNumber(), getId(), getFa(), getGa()},
                new String[] {other.getName(), other.getPhNumber(), other.getId(), other.getFa(), other.getGa()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhNumber(), getId(), getFa(), getGa());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [name=" + name + ", phNumber="
                + phNumber + ", id=" + id + ", fa=" + fa + ", ga=" + ga + ", docType=" + "user" + "]";
    }
}

