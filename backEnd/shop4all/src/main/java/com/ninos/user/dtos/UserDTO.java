package com.ninos.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninos.cart.dtos.CartDTO;
import com.ninos.order.dtos.OrderDTO;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // password can be written in the request body, but will not be included in the response
    private String password;

    private List<OrderDTO> orders;
    private CartDTO cart;



}
