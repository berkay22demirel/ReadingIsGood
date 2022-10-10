package com.berkay22demirel.readingisgood.service.mapper;

public interface Mappable<T, K> {

    T convertToEntity(K dto);

    K convertToDto(T entity);

}
