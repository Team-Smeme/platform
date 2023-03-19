package com.snim.configuration;

import org.springframework.context.annotation.Profile;

import java.io.IOException;

public interface SeparatedProfileConfigurable<PRODUCT> {
    @Profile({"test", "local"})
    PRODUCT embedded() throws IOException, InterruptedException;

    @Profile({"!test", "!local"})
    PRODUCT remote();
}
