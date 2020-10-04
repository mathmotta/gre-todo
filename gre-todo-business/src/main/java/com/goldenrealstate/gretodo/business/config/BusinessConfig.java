package com.goldenrealstate.gretodo.business.config;

import com.goldenrealstate.gretodo.business.IBusinessPackage;
import com.goldenrealstate.gretodo.data.config.DataConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures the business module by package class and not strings
 *
 * @author Mathews Motta
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = {IBusinessPackage.class})
@Import({DataConfig.class})
@EnableTransactionManagement
public class BusinessConfig {
}
