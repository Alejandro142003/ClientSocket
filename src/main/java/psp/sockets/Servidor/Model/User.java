package psp.sockets.Servidor.Model;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class User implements Serializable {
    private UUID id;
    private String name;
    private String password;
    private String role;
    private Set<Account> accounts = new HashSet<>();
}