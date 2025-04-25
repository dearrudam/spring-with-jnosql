package com.github.dearrudam.springwithjnosqlmongodb.sample;

import jakarta.data.repository.Repository;
import org.eclipse.jnosql.mapping.NoSQLRepository;

@Repository
public interface Developers extends NoSQLRepository<Developer, String> {

}
