package di.dilogin.minecraft.ext.nlogin.event;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

import di.dilogin.controller.LangManager;
import di.dilogin.entity.CodeGenerator;
import di.dilogin.entity.DIUser;
import di.dilogin.entity.TmpMessage;
import di.dilogin.minecraft.cache.TmpCache;
import di.dilogin.minecraft.event.UserLoginEvent;
import di.dilogin.minecraft.event.custom.DILoginEvent;

import com.nickuc.login.api.event.bukkit.auth.AuthenticateEvent;

public class UserLoginEventnLoginImpl implements UserLoginEvent {

	@Override
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String playerName = event.getPlayer().getName();
		if (!userDao.contains(playerName)) {
			initPlayerRegisterRequest(event, playerName);
		} else {
			initPlayerLoginRequest(event, playerName);
		}
	}

	@Override
	public void initPlayerLoginRequest(PlayerJoinEvent event, String playerName) {
		TmpCache.addLogin(playerName, null);
		Optional<DIUser> userOpt = userDao.get(playerName);
		if (!userOpt.isPresent())
			return;

		DIUser user = userOpt.get();

		event.getPlayer().sendMessage(LangManager.getString(user, "login_request"));
		sendLoginMessageRequest(user.getPlayerBukkit().get(), user.getPlayerDiscord().get());
	}

	@Override
	public void initPlayerRegisterRequest(PlayerJoinEvent event, String playerName) {
		String code = CodeGenerator
				.getCode(api.getInternalController().getConfigManager().getInt("register_code_length"), api);
		TmpCache.addRegister(playerName, new TmpMessage(event.getPlayer(), null, null, code));
	}

	/**
	 * Event when a user logs in with authme.
	 * 
	 * @param event LoginEvent.
	 */
	@EventHandler
    public void AuthenticateEvent(final AuthenticateEvent event) {
		Player player = event.getPlayer();
        String playerName = player.getName();

        if (!userDao.contains(playerName)) {
            initPlayernLoginRegisterRequest(player, playerName);
        }

        Bukkit.getScheduler().runTask(api.getInternalController().getPlugin(),
                () -> Bukkit.getPluginManager().callEvent(new DILoginEvent(player)));
    }

	/**
	 * If the user logged in with Authme is not registered with DILogin, it prompts
	 * him to register.
	 * 
	 * @param event      Authme login event.
	 * @param playerName Player's name.
	 */
	public void initPlayernLoginRegisterRequest(Player player, String playerName) {
		String code = CodeGenerator
				.getCode(api.getInternalController().getConfigManager().getInt("register_code_length"), api);
		String command = api.getCoreController().getBot().getPrefix() + api.getInternalController().getConfigManager().getString("register_command") + " " + code;
		TmpCache.addRegister(playerName, new TmpMessage(player, null, null, code));
		player.sendMessage(LangManager.getString(player, "register_opt_request")
				.replace("%register_command%", command));
	}
}
