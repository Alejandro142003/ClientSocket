package psp.cajeros.Model;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class User {
    private UUID id;
    private String name;
    private String password;
    private String role;
    private Set<Account> accounts = new LinkedHashSet<>();
}