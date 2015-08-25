package com.lime.mypol.result;

import java.util.List;

/**
 * Created by Administrator on 2015-08-26.
 */
public class AddressResult {

    private List<AddressComponents> address_components;
    private String formatted_address;

    public List<AddressComponents> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponents> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
