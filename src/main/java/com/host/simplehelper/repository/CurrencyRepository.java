package com.host.simplehelper.repository;

import com.host.simplehelper.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
public interface CurrencyRepository extends JpaRepository<Currency, LocalDate> {

}
