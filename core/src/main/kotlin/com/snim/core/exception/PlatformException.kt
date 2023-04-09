package com.snim.core.exception

import java.lang.Exception

abstract class PlatformException(override val message : String?) : Exception() {

}