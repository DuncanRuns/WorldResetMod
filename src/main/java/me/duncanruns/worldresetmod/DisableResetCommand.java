package me.duncanruns.worldresetmod;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.io.FileWriter;

public class DisableResetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("reset-off").requires((serverCommandSource) -> {
            return serverCommandSource.hasPermissionLevel(2);
        })).executes((commandContext) -> {
            return execute((ServerCommandSource) commandContext.getSource());
        })));
    }

    public static int execute(ServerCommandSource source){
        try {
            File worldResetFile = new File("disableWorldReset");
            if(worldResetFile.exists()){
                LiteralText response = new LiteralText("World resets are already disabled");
                source.sendFeedback(response,false);
                return 0;
            } else{
                LiteralText response = new LiteralText("World resets disabled");
                FileWriter worldResetFileWriter = new FileWriter(worldResetFile);
                worldResetFileWriter.close();
                source.sendFeedback(response,true);
                return 1;
            }
        } catch(Exception ignored){
            return 0;
        }
    }
}
