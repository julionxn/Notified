package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.util.IEntityDataSaver;
import net.pulga22.notified.util.NotificationSaver;

import java.util.Map;

public class ReceiveNotificationS2CPacket {
    public static void send(MinecraftClient client, ClientPlayNetworkHandler handler,
                            PacketByteBuf buf, PacketSender sender){
        //On client
        assert client.player != null;
        //Changes notifications client-side file
        Map<String,String> map = buf.readMap(PacketByteBuf::readString, PacketByteBuf::readString);
        client.execute(()-> {
            NotificationSaver.appendNotification(map.get("title"), map.get("message"));
        });
        //New notification so sets read to false
        ((IEntityDataSaver) client.player).getPersistentData().putBoolean("read", false);
    }
}
