package voltbutcoding.meowzers;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Meowzers.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                MeowPacket.TYPE,
                MeowPacket.STREAM_CODEC,
                new ServerMeowPayloadHandler()
        );
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Meowzers.LOGGER.info("Meow meows initialized :3 :3 ");
        NeoForge.EVENT_BUS.addListener(ClientModEvents::onClientTick);
    }
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        Meowzers.meowKey = new KeyMapping("key.meowzers.meow",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.meowzers.keys");
        event.register(Meowzers.meowKey );
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        if (Meowzers.meowKey.consumeClick()) {
            if (Minecraft.getInstance().player == null) return;
            Minecraft.getInstance().player.playSound(Meowzers.MEOW, 1.0F, 1.0F);
            PacketDistributor.sendToServer(new MeowPacket(1));
        }
    }
}