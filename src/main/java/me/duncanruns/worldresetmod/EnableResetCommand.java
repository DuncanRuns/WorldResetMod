package me.duncanruns.worldresetmod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.io.File;
import java.io.FileWriter;

public class EnableResetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("reset-on").requires((serverCommandSource) -> {
            return serverCommandSource.hasPermissionLevel(2);
        })).executes((commandContext) -> {
            return execute((ServerCommandSource) commandContext.getSource());
        })));
    }

    public static int execute(ServerCommandSource source){
        try {
            File worldResetFile = new File("disableWorldReset");
            if(!worldResetFile.exists()){
                LiteralText response = new LiteralText("World resets are already enabled");
                source.sendFeedback(response,false);
                return 0;
            } else{
                LiteralText response = new LiteralText("World resets enabled");
                worldResetFile.delete();
                source.sendFeedback(response,true);
                return 1;
            }
        } catch(Exception ignored){
            return 0;
        }
    }
}
