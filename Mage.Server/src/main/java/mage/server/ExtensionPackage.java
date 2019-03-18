

package mage.server;

import mage.cards.ExpansionSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main entry point for external packages.
 * @author Lymia
 */
public abstract class ExtensionPackage {
    protected final List<ExpansionSet> expansions = new ArrayList<>();
    protected final Map<String, Class> deckTypes = new HashMap<>();
    protected final Map<String, Class> draftCubes = new HashMap<>();

    /**
     * @return A list of expansions included in this custom set package.
     */
    public List<ExpansionSet> getSets() {
        return expansions;
    }

    /**
     * @return A list of draft cubes included in this custom set package.
     */
    public Map<String, Class> getDraftCubes() {
        return draftCubes;
    }

    /**
     * @return A list of deck types included in this custom set package.
     */
    public Map<String, Class> getDeckTypes() {
        return deckTypes;
    }

    /**
     * @return The classloader cards should be loaded from for this custom set package.
     */
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }
}
