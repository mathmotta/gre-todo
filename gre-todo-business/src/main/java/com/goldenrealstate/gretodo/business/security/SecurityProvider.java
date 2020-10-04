package com.goldenrealstate.gretodo.business.security;

import org.springframework.stereotype.Service;

/**
 * This is a "future proof" security resolver for audit trail
 * Right now, there's no identity provider decided and thus it returns a default GRE user name
 * In a real world scenario, the system should get the authenticated user from the context and fill the audit columns with that information.
 *
 * @author Mathews Motta
 * @since 1.0
 */
@Service
public class SecurityProvider {
    public String getCurrentUser() {
//        SecurityContext sc = SecurityContextHolder.getContext();
//        Authentication authentication = sc == null ? null : sc.getAuthentication();
//        if (authentication != null) {
//            Object details = authentication.getDetails();
//            // Gets the user from the chosen identity provider
//            // e.g.: return ((SimpleKeycloakAccount) details).getPrincipal().getName()
//        }
        return "DefaultGreUser";
    }
}
