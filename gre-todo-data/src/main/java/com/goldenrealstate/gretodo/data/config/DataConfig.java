package com.goldenrealstate.gretodo.data.config;

import com.goldenrealstate.gretodo.data.IDataPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures the data module by package class and not strings
 *
 * @author Mathews Motta
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = {IDataPackage.class})
@EntityScan(basePackageClasses = {IDataPackage.class})
@EnableJpaRepositories(basePackageClasses = {IDataPackage.class})
@EnableTransactionManagement
public class DataConfig {
}
