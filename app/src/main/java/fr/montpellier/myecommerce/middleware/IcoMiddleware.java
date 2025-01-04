package fr.montpellier.myecommerce.middleware;

import fr.montpellier.myecommerce.activity.MiddlewareActivity;

public interface IcoMiddleware {
    boolean verify_and_redirect(MiddlewareActivity activity);
    void redirect(MiddlewareActivity activity);
}