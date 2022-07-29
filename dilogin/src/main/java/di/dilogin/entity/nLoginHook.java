package di.dilogin.entity;

import org.bukkit.entity.Player;

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
	private static final nLoginAPI nloginapi = nLoginAPI.getAPI();

	/**
	 * Start the player session
	 * 
	 * @param player Bukkit player.
	 */
	public static void login(Player player) {
		if (nLoginAPI.isRegistered(player.getName()))
			nLoginAPI.forceLogin(player);
	}

	/**
	 * Register a player
	 * 
	 * @param player   Bukkit player.
	 * @param password Default password.
	 */
	public static void register(Player player, String password) {
		if (!nLoginAPI.isRegistered(player.getName()))
			nLoginAPI.performRegister(player, password);
	}
}
