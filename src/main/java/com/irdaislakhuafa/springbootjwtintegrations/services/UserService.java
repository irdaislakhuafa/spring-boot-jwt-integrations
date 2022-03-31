package com.irdaislakhuafa.springbootjwtintegrations.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.springbootjwtintegrations.models.dtos.UserDto;
import com.irdaislakhuafa.springbootjwtintegrations.models.entities.User;
import com.irdaislakhuafa.springbootjwtintegrations.models.entities.utils.Gender;
import com.irdaislakhuafa.springbootjwtintegrations.models.entities.utils.UserRoles;
import com.irdaislakhuafa.springbootjwtintegrations.models.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService, BaseService<User, UserDto> {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username/Id \"%s\" not found!", username)));
    }

    @Override
    public Optional<User> save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return Optional.of(userRepository.save(entity));
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.of(userRepository.save(entity));
    }

    @Override
    public User mapDtoToEntity(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .gender(Gender.valueOf(dto.getGender().toUpperCase()))
                .userRoles(UserRoles.valueOf(dto.getUserRoles().toUpperCase()))
                .active(true)
                .build();
    }

    @Override
    public List<User> mapDtosToEntities(List<UserDto> dtos) {
        return dtos.stream().map((dto) -> mapDtoToEntity(dto)).collect(Collectors.toList());
    }

}
