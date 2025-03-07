package voltbutcoding.meowzers;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MeowPacket(int meow) implements CustomPacketPayload {
    public static final Type<MeowPacket> TYPE =new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("meowzers", "meow_data"));

    public static final StreamCodec<ByteBuf, MeowPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MeowPacket::meow,
            MeowPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
