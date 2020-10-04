package com.goldenrealstate.gretodo.rest.config;

import com.goldenrealstate.gretodo.business.config.BusinessConfig;
import com.goldenrealstate.gretodo.rest.IRestPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackageClasses= {IRestPackage.class})
@Import({BusinessConfig.class})
@EnableTransactionManagement
public class RestConfig {

}