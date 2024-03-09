package psp.sockets.Servidor.Model;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Account implements Serializable {
    private UUID id;
    private int acountNumber;
    private float balance;
    private User user;
    private Set<Transaction> transactions = new LinkedHashSet<>();
}
