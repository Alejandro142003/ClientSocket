package psp.sockets.Servidor.Model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Transaction implements Serializable {
    private UUID id;
    private String type;
    private float amount;
    private LocalDateTime date;
    private Account account;
    private Account accountDestiny;
}
