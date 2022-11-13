package com.amkatek.springmysql;
import org.springframework.data.repository.CrudRepository;
import com.amkatek.springmysql.User;
public interface UserRepository extends CrudRepository<User, Integer> {
}
