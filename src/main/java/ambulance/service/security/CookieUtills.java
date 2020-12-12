package ambulance.service.security;
import ambulance.service.models.userTypes;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;

public class CookieUtills
{

    public String createToken(String userID,String username,userTypes role)
    {
        Algorithm algorithm=Algorithm.HMAC256("ImaginaryHospital");
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
        try {
            String user = com.auth0.jwt.JWT.decode(token).getClaim("role").asString();
            userTypes[] available_types = userTypes.values();
            for (userTypes role : available_types) {
                if (user.equalsIgnoreCase(role.name()))
                    return role;
            }
            return userTypes.GUEST;
        }
        catch (Exception e){
            return userTypes.GUEST;
        }
    }
    public boolean isAdmin(String token)
    {
        return getRole(token)==userTypes.ADMIN;
    }
    public String getName(String token)
    {
        try {
            return com.auth0.jwt.JWT.decode(token).getClaim("username").asString();
        }
        catch (Exception e){
            return "";
        }

    }
    public String getID(String token)
    {
        return com.auth0.jwt.JWT.decode(token).getClaim("userID").asString();
    }
    public boolean isLoggedin(String token){
        System.out.print(getRole(token).name());
        return !(getRole(token) == userTypes.GUEST); }
}
