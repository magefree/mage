package mage.server.draft;

import mage.cards.decks.Deck;
import mage.game.draft.DraftCube;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author LevelX2, JayDi85
 */
public enum CubeFactory {

    instance;
    private static final Logger logger = Logger.getLogger(CubeFactory.class);

    private final Map<String, Class> draftCubes = new LinkedHashMap<>();


    public DraftCube createDraftCube(String draftCubeName) {

        DraftCube draftCube;
        try {
            Constructor<?> con = draftCubes.get(draftCubeName).getConstructor();
            draftCube = (DraftCube) con.newInstance();
        } catch (Exception ex) {
            logger.fatal("CubeFactory error", ex);
            return null;
        }
        logger.debug("Draft cube created: " + draftCube.getName());

        return draftCube;
    }

    public DraftCube createDeckDraftCube(String draftCubeName, Deck cubeFromDeck) {

        DraftCube draftCube;
        try {
            Constructor<?> con = draftCubes.get(draftCubeName).getConstructor(Deck.class);
            draftCube = (DraftCube) con.newInstance(cubeFromDeck);
        } catch (Exception ex) {
            logger.fatal("CubeFactory error", ex);
            return null;
        }
        logger.debug("Draft cube created: " + draftCube.getName());

        return draftCube;
    }

    public Set<String> getDraftCubes() {
        return draftCubes.keySet();
    }

    public void addDraftCube(String configName, Class configCubeClass) {
        // store cubes by auto-generated names, not from config
        if (configCubeClass == null) {
            return;
        }

        DraftCube draftCube = null;
        try {
            Constructor<?> con = configCubeClass.getConstructor();
            draftCube = (DraftCube) con.newInstance();
            if (this.draftCubes.containsKey(draftCube.getName())) {
                throw new IllegalArgumentException("already exists " + draftCube.getName());
            }
        } catch (Exception e) {
            logger.error("Can't create draft cube named by " + configName, e);
            return;
        }

        this.draftCubes.put(draftCube.getName(), configCubeClass);
    }
}
