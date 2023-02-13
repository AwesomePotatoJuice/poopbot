package ru.surin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.surin.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}
