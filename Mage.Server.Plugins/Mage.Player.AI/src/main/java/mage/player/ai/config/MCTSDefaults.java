package mage.player.ai.config;

public class MCTSDefaults {
    public static MCTSDefaults CURRENT = loadOrDefault();

    public double searchTimeout = 4.0;
    public int searchBudget = 1000;
    public double backpropDiscount = 0.99;

    public double priorTemp = 1.5;
    public double priorBonus = 0.1;

    public boolean noNoise = true;
    public double dirichletNoiseEps = 0;
    public double selectionTemperature = 0;

    public boolean noPolicyPriority = true;
    public boolean noPolicyTarget = true;
    public boolean noPolicyUse = true;
    public boolean noPolicyOpponent = true;


    private static MCTSDefaults loadOrDefault() {
        String path = System.getProperty("magezero.play.config");
        if (path == null) return new MCTSDefaults();
        try {
            Class<?> loader = Class.forName("org.mage.magezero.PlayConfigLoader");
            return (MCTSDefaults) loader.getMethod("load", String.class).invoke(null, path);
        } catch (ClassNotFoundException e) {
            return new MCTSDefaults();
        } catch (Exception e) {
            throw new RuntimeException("failed to load " + path, e);
        }
    }
}