package com.gfstabile.java.carrest.exception.impl.car;

import com.gfstabile.java.carrest.exception.AbstractServiceException;

/**
 * Exception used when you try to insert a new car
 * but a unique key exception occurs during the insert.
 *
 * @author G. F. Stabile
 */
public class CarAlreadyExistsException extends AbstractServiceException {}
