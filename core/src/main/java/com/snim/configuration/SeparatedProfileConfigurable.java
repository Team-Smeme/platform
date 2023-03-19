package com.snim.configuration;

import org.springframework.context.annotation.Profile;

public interface SeparatedProfileConfigurable<PRODUCT> {
    @Profile({"test", "local"})
    PRODUCT embedded();

    @Profile({"!test", "!local"})
    PRODUCT remote();
}
