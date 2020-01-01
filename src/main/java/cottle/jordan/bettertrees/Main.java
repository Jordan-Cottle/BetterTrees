package cottle.jordan.bettertrees;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Server server;
    private PluginManager pluginManager;

    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        System.out.println("It worked!");

        server = this.getServer();
        pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new TreeListener(), this);
    }

    // This code is called before the server stops and after the /reload command
    @Override
    public void onDisable() {
        System.out.println("All done!");
    }
}
