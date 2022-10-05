package uz.bob.app_phone_purchases.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.bob.app_phone_purchases.entity.Role;

import java.util.Date;

@Component
public class JwtProvider {

    String secretKey="thisIsSecretKey";
    private  final long expireTime =1000*3600*24;
    public String generateToken(String username, Role role){
        Date expireDate = new Date(System.currentTimeMillis()+expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", role.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public String getUsernameFromToken(String token){
        try {
            String username = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        }catch (Exception e){
            return null;
        }

    }






















//
//    public static void main(String[] args) {
//        System.out.println(countSegments(", , , ,        a, eaefa"));
//
//    }
//    public static String isPalindrome(String s) {
////        if(s.equals(" ") || s.length()==1) return true;
//         s = s.toLowerCase();
//        String res="";
//        String res2="";
//        for(char c:s.toCharArray()){
//            if(c>=65 && c<=90 || c>=97 && c<=122 || c>=48 && c<=57){
//                res+=c;
//            }
//
//        }
////        for(int i=res.length()-1;i>=0;i--;int j=0;j<10;j++){
////            res2+=res.charAt(i);
////        }
////        return res.equals(res2);
//        return res2 ;
//    }
//    public static int countSegments(String s) {
//        int segmentCount = 0;
//
//              for (int i = 0; i < s.length(); i++) {
//                 if ((i == 0 || s.charAt(i-1) == ' ') && s.charAt(i) != ' ') {
//                      segmentCount++;
//                  }
//              }
//
//              return segmentCount;
////        if(s.length()==0) return 0;
////        int count=0;
////        s=s.toUpperCase();
////        char c[]=s.toCharArray();
////        for(int i=0;i<c.length-1;i++){
////            if(c[i]>=65 && c[i]<=90 && (c[i+1]!=32 || c[i+1]!=44)){
////                count++;
////            }
////        }
////        if(c[s.length()-1]>=65 && c[s.length()-1]<=90){
////            count++;
////        }
////        return count;
//
//    }
    /*
    "The one-hour drama series Westworld is a dark odyssey about the dawn of artificial consciousness and the evolution of sin.
     Set at the intersection of the near future and the reimagined past, it explores a world in which every human appetite,
     no matter how noble or depraved, can be indulged."
     */

    /**
     * class Solution {
     *     public int countSegments(String s) {
     *         int segmentCount = 0;
     *
     *         for (int i = 0; i < s.length(); i++) {
     *             if ((i == 0 || s.charAt(i-1) == ' ') && s.charAt(i) != ' ') {
     *                 segmentCount++;
     *             }
     *         }
     *
     *         return segmentCount;
     *     }
     * }
     */
}
