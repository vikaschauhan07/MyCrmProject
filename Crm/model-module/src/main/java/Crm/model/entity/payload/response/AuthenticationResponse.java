package Crm.model.entity.payload.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  
  private String message;
  
  public AuthenticationResponse(String access_token,String refresh_token) {
	  this.accessToken = access_token;
	  this.refreshToken = refresh_token;
  }
  public AuthenticationResponse(String message) {
	  this.message = message;
  }
  
}
