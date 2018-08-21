package it.giorgiopagnoni.springrestws

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class SpringApplicationContext: ApplicationContextAware {
    companion object {
        private lateinit var CONTEXT: ApplicationContext
        fun getBean(beanName: String): Any? = CONTEXT.getBean(beanName)
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        CONTEXT = applicationContext
    }


}