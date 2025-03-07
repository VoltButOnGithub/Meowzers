package voltbutcoding.meowzers;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class ServerMeowPayloadHandler implements IPayloadHandler<MeowPacket> {
    @Override
    public void handle(MeowPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().playSound(Meowzers.MEOW, 3.0F, 1.0F);
        });
    }
}
