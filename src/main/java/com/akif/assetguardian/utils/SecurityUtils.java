package com.akif.assetguardian.utils;

import com.akif.assetguardian.model.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


//İşlemler için güvenlik bağlamından geçerli kullanıcı bilgilerini alır.
public class SecurityUtils {

    private SecurityUtils() {
    }
    public static Integer getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof MyUserDetails myUserDetails){
            return myUserDetails.getUser().getId();
        }
        throw new RuntimeException("Bu işlem için giriş yapmanız gerekmektedir!");
    }

    public static boolean hasRole(String role){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
        }
        return false;
    }

    public static boolean isOwnerOrAdmin(int ownerId) {
        if (hasRole("ROLE_ADMIN")) {
            return true;
        }

        Integer currentUserId = getCurrentUserId();

        if (currentUserId == null){
            return false;
        }

        return currentUserId == ownerId;
    }
}
