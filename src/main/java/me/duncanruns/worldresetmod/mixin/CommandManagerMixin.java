package me.duncanruns.worldresetmod.mixin;

import com.mojang.brigadier.CommandDispatcher;
import me.duncanruns.worldresetmod.DisableResetCommand;
import me.duncanruns.worldresetmod.EnableResetCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Shadow
    @Final
    private
    CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void registerResetCommands(CommandManager.RegistrationEnvironment environment, CallbackInfo info) {
        DisableResetCommand.register(dispatcher);
        EnableResetCommand.register(dispatcher);
    }
}
