package di.dilogin.minecraft.ext.nlogin.event;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import di.dilogin.controller.DILoginController;
import di.dilogin.dao.DIUserDao;
import com.nickuc.login.api.event.bukkit.command.UnregisterEvent;
import com.nickuc.login.api.enums.event.UnregisterSource;

/**
 * AuthMe related events.
 */
public class nLoginEvents implements Listener {

	/**
	 * User management.
	 */
	private DIUserDao userDao = DILoginController.getDIUserDao();

	@EventHandler
	public void UnregisterEvent(UnregisterEvent event) {
		Player player = event.getPlayer();
		Optional<Player> optPlayer = Optional.ofNullable(player);
		if (optPlayer.isPresent())
			unregister(optPlayer.get().getName());
	}

	/**
	 * Unregister user from DILogin.
	 */
	private void unregister(String playerName) {
		userDao.remove(playerName);
	}
}
