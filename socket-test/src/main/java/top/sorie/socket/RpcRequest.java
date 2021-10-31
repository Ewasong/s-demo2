package top.sorie.socket;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}