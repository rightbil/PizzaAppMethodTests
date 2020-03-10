package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<XOrder, Long> {

    @Override
    default <S extends XOrder> S save(S s) {
        return null;
    }

    @Override
    default <S extends XOrder> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    default Optional<XOrder> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    default boolean existsById(Long aLong) {
        return false;
    }

    @Override
    default Iterable<XOrder> findAll() {
        return null;
    }

    @Override
    default Iterable<XOrder> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    default long count() {
        return 0;
    }

    @Override
    default void deleteById(Long aLong) {

    }

    @Override
    default void delete(XOrder xOrder) {

    }

    @Override
    default void deleteAll(Iterable<? extends XOrder> iterable) {

    }

    @Override
    default void deleteAll() {

    }


}
