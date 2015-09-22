package mage.cards.decks.generator;

/**
 *
 * @author BetaSteward
 */
public class DeckOptions {
    
    private String selectedColors;
    private String selectedFormat;
    private int deckSize;
    private boolean singleton;
    private boolean useArtifacts;
    private boolean useNonBasicLand;
    
    public DeckOptions(String selectedColors, String selectedFormat, int deckSize, boolean singleton, boolean useArtifacts, boolean useNonBasicLand) {
        this.selectedColors = selectedColors;
        this.selectedFormat = selectedFormat;
        this.deckSize = deckSize;
        this.singleton = singleton;
        this.useArtifacts = useArtifacts;
        this.useNonBasicLand = useNonBasicLand;
    }

    public String getSelectedColors() {
        return selectedColors;
    }

    public String getSelectedFormat() {
        return selectedFormat;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean useArtifacts() {
        return useArtifacts;
    }

    public boolean useNonBasicLand() {
        return useNonBasicLand;
    }
    
}
