package di.dilogin.entity;

import org.bukkit.entity.Player;
import di.dilogin.BukkitApplication;
import com.nickuc.login.api.nLoginAPI;

/**
 * Class that interacts with the authme api
 */
public class nLoginHook {

	/**
	 * Prohibits instantiation of the class.
	 */
	private nLoginHook() {
		throw new IllegalStateException();
	}

	/**
	 * Authme api.
	 */
	private static final nLoginAPI nloginapi = nLoginAPI.getApi();

	/**
	 * Start the player session
	 * 
	 * @param player Bukkit player.
	 */
	public static void login(Player player) {
		if (nloginapi.isRegistered(player.getName()))
			nloginapi.forceLogin(player.getName());
	}

	/**
	 * Register a player
	 * 
	 * @param player   Bukkit player.
	 * @param password Default password.
	 */
	public static void register(Player player, String password) {
		if (!nloginapi.isRegistered(player.getName())) {
			BukkitApplication.getPlugin().getLogger().info("Not registred. Regisering...");
			nloginapi.performRegister(player.getName(), password);
		} else {
			BukkitApplication.getPlugin().getLogger().info("Registred.");
			
		}
	}
}
