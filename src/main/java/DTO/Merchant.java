package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Merchant {

@JsonProperty("client_id")
private String clientId;

@JsonProperty("client_name")
private String clientName;

@JsonProperty("merchant_state")
private String merchantState;

public String getClientId() {
	return clientId;
}

public void setClientId(String clientId) {
	this.clientId = clientId;
}

public String getClientName() {
	return clientName;
}

public void setClientName(String clientName) {
	this.clientName = clientName;
}

public String getMerchantState() {
	return merchantState;
}

public void setMerchantState(String merchantState) {
	this.merchantState = merchantState;
}


}
