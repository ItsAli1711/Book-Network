package com.ali.book_network.user;

import com.ali.book_network.role.role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

   @Id
   @GeneratedValue
   private Integer id;
   private String firstname;
   private String lastname;
   private LocalDate dateOfBirth;
   @Column(unique = true)
   private String email;
   private String password;
   private boolean accountLocked;
   private boolean enabled;

   @ManyToMany(fetch = FetchType.EAGER)
   private List<role> roles;


    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime LastModifiedDate;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {                                 //This indicates that the method returns a collection of objects that implement the GrantedAuthority interface.
        return this.roles                                                                            //this.roles.stream(): Converts the list of roles associated with the user into a stream for processing.
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))        //map(r -> new SimpleGrantedAuthority(r.getName())): Maps each Role object to a SimpleGrantedAuthority, which is a Spring Security class representing an authority granted to the user. The authority name is typically the role's name.
                .collect(Collectors.toList());                          //: Collects the mapped authorities into a List and returns it.
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    private String fullName()
    {
        return firstname + " " + lastname;
    }
}
