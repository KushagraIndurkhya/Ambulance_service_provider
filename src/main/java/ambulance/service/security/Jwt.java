package ambulance.service.security;
import ambulance.service.models.userTypes;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class Jwt
{
    Algorithm algorithm=Algorithm.HMAC256("ImaginaryHospitalRocks");
    public String createToken(String userID,String username,userTypes role)
    {
        String token= com.auth0.jwt.JWT.create()
                .withIssuer("AmbulanceSecurity")
                .withClaim("username",username)
                .withClaim("userID",userID)
                .withClaim("role",role.toString())
                .sign(algorithm);

        return token;
    }
    public userTypes getRole(String token)
    {
        String user= com.auth0.jwt.JWT.decode(token).getClaim("role").asString();
        userTypes available_types[]=userTypes.values();
        for (userTypes role:available_types)
            {
                if(user.equalsIgnoreCase(role.name()))
                    return role;
            }
        return userTypes.GUEST;
    }
    public boolean isAdmin(String token)
    {
        return getRole(token)==userTypes.ADMIN;
    }
    public String getName(String token)
    {
        return com.auth0.jwt.JWT.decode(token).getClaim("username").asString();
    }
    public String getID(String token)
    {
        return com.auth0.jwt.JWT.decode(token).getClaim("userID").asString();
    }
}
