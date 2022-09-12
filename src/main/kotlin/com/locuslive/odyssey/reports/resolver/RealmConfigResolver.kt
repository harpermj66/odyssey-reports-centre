package com.locuslive.odyssey.reports.resolver

import io.quarkus.oidc.OidcRequestContext
import io.quarkus.oidc.OidcTenantConfig
import io.quarkus.oidc.TenantConfigResolver
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RealmConfigResolver(@ConfigProperty(name = "auth-server-url") val authServerUrl: String ): TenantConfigResolver {

    companion object {
        const val REALM_HEADER = "realm"
        const val MASTER_REALM = "master"
        const val CLIENT = "odyssey"
    }

    override fun resolve(
        routingContext: RoutingContext,
        requestContext: OidcRequestContext<OidcTenantConfig>
    ): Uni<OidcTenantConfig> {

        val request = routingContext.request()
        val headers = request?.headers()
        val realm = headers?.get(REALM_HEADER) ?: MASTER_REALM
        val config = OidcTenantConfig()
        config.setTenantId(realm)
        config.setClientId(CLIENT)
        config.setAuthServerUrl("$authServerUrl/auth/realms/$realm")

        return Uni.createFrom().item(config)
    }
}