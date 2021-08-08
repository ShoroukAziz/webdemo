package com.isfp.app.ws.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.isfp.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	
	
	@Query(value="SELECT * FROM  users u WHERE u.email_verification_status = 'true' " ,
			countQuery="SELECT count(*) from USERS u WHERE u.email_verification_status = 'true'",
			nativeQuery=true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
	
	
	@Query(value="SELECT * FROM users u WHERE u.first_name =?1" , nativeQuery=true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	
	@Query(value="SELECT * FROM users u WHERE u.last_name =:lastName" , nativeQuery=true)
	List<UserEntity> findUserByLastName(@Param("lastName") String lastName);


}
