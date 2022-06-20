package hac.ex4;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import hac.ex4.beans.Basket;

@Configuration
public class BeanConfiguration {


    @Bean
    @SessionScope
    public Basket sessionBasket() {
        return new Basket();
    }//the cart

    @Bean
    public MessageSource messageSource() { //enables custom form error messages
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        return resourceBundleMessageSource;
    }

}
