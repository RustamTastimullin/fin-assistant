package com.host.finassistant.repository;

import com.host.finassistant.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Репозиторий таблицы отношения валют к рублю.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
public interface CurrencyRepository extends JpaRepository<Currency, LocalDate> {

}
