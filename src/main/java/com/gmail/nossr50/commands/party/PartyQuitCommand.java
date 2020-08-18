package com.gmail.nossr50.commands.party;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.events.party.McMMOPartyChangeEvent.EventReason;
import com.gmail.nossr50.locale.LocaleLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PartyQuitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            Player player = (Player) sender;

            if (mcMMO.getUserManager().getPlayer((Player) sender) == null) {
                sender.sendMessage(LocaleLoader.getString("Profile.PendingLoad"));
                return true;
            }

            McMMOPlayer mmoPlayer = mcMMO.getUserManager().getPlayer(player);
            Party playerParty = mmoPlayer.getParty();

            if (!mcMMO.getPartyManager().handlePartyChangeEvent(player, playerParty.getPartyName(), null, EventReason.LEFT_PARTY)) {
                return true;
            }

            mcMMO.getPartyManager().removeFromParty(mmoPlayer);
            sender.sendMessage(LocaleLoader.getString("Commands.Party.Leave"));
            return true;
        }
        sender.sendMessage(LocaleLoader.getString("Commands.Usage.1", "party", "quit"));
        return true;
    }
}
