
package mage.abilities;

import java.util.UUID;
import mage.game.Game;

/**
 * The ActivationInfo class holds the information how often an ability of an
 * object was activated during a turn. It handles the check, if the object is
 * still the same, so for example if a permanent left battlefield and returns,
 * the counting of activations happens for each object.
 *
 * @author LevelX2
 */
public class ActivationInfo {

    protected int turnNum = 0;
    protected int activationCounter = 0;
    protected String key;

    public static ActivationInfo getInstance(Game game, UUID sourceId) {
        return ActivationInfo.getInstance(game, sourceId, game.getState().getZoneChangeCounter(sourceId));
    }

    public static ActivationInfo getInstance(Game game, UUID sourceId, int zoneChangeCounter) {
        String key = "ActivationInfo" + sourceId.toString() + zoneChangeCounter;
        Integer activations = (Integer) game.getState().getValue(key);
        ActivationInfo activationInfo;
        if (activations != null) {
            Integer turnNum = (Integer) game.getState().getValue(key + 'T');
            activationInfo = new ActivationInfo(game, turnNum, activations);
        } else {
            activationInfo = new ActivationInfo(game, game.getTurnNum(), 0);
        }
        activationInfo.setKey(key);
        return activationInfo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected ActivationInfo(Game game, int turnNum, int activationCounter) {
        this.turnNum = turnNum;
        this.activationCounter = activationCounter;
    }

    public void addActivation(Game game) {
        if (game.getTurnNum() != turnNum) {
            activationCounter = 1;
            turnNum = game.getTurnNum();
        } else {
            activationCounter++;
        }
        game.getState().setValue(key, activationCounter);
        game.getState().setValue(key + 'T', turnNum);
    }

    public int getActivationCounter() {
        return activationCounter;
    }
}
