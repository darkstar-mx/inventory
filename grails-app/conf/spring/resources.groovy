import mx.com.acerozin.security.AuthManager

// Place your Spring DSL code here
beans = {
    authenticationManager(AuthManager)

    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("es", "ES")
        java.util.Locale.setDefault(defaultLocale)
    }

}
