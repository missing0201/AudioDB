package com.pm.audiodbsecurity;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AudioDBSecurityConfig.class)

public @interface EnableAudioDBSecurity {
}
