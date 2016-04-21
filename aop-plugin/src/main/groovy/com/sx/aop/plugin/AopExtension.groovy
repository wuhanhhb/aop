package com.sx.aop.plugin

class AopExtension {
    def aspectjVersion = '1.8.6'

    def getAspectjVersion() {
        return aspectjVersion
    }

    void setAspectjVersion(aspectjVersion) {
        this.aspectjVersion = aspectjVersion
    }
}