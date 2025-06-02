package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JayDi85
 */
public abstract class TargetPointerImpl implements TargetPointer {

    // Store custom data here. Use it to keep unique values for ability instances on stack (example: Gruul Ragebeast)
    private Map<String, String> data;

    private boolean initialized = false;

    protected TargetPointerImpl() {
        super();
    }

    protected TargetPointerImpl(final TargetPointerImpl targetPointer) {
        super();
        if (targetPointer.data != null) {
            this.data = new HashMap<>();
            this.data.putAll(targetPointer.data);
        }
        this.initialized = targetPointer.initialized;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized() {
        this.initialized = true;
    }

    @Override
    public Player getControllerOfFirstTargetOrLKI(Game game, Ability source) {
        Player targetController = null;
        Permanent permanent = this.getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            targetController = game.getPlayer(permanent.getControllerId());
        } else {
            Spell spell = game.getSpellOrLKIStack(this.getFirst(game, source));
            if (spell != null) {
                targetController = game.getPlayer(spell.getControllerId());
            }
        }
        return targetController;
    }

    @Override
    public String getData(String key) {
        if (data == null) {
            return "";
        }
        return data.getOrDefault(key, "");
    }

    @Override
    public TargetPointer withData(String key, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }

}
