//package co.id.employeetrainingsecurity.util;
//
//import java.util.Date;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;
//
//public class JwtUtils {
//    // Secret key untuk tanda tangan JWT
//    private final String JWT_SECRET = "your-secret-key"; // Simpan di tempat aman, misalnya di application.properties
//
//    // Waktu kedaluwarsa token (dalam milidetik)
//    private final long JWT_EXPIRATION = 3600000; // 1 jam
//
//    /**
//     * Generate token JWT untuk user
//     * @param username nama pengguna yang dimasukkan ke token
//     * @return token JWT
//     */
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username) // Data utama yang disimpan dalam token
//                .setIssuedAt(new Date()) // Waktu pembuatan token
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)) // Waktu kedaluwarsa
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // Algoritma tanda tangan
//                .compact();
//    }
//
//    /**
//     * Validasi token JWT
//     * @param token JWT yang akan divalidasi
//     * @return true jika valid, false jika tidak valid
//     */
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token); // Memeriksa token
//            return true;
//        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException e) {
//            System.err.println("JWT tidak valid: " + e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * Mendapatkan username dari token JWT
//     * @param token JWT
//     * @return username yang disimpan dalam token
//     */
//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject(); // Mengambil username dari "subject"
//    }
//}
