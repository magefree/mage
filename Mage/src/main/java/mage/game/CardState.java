package mage.game;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.util.Copyable;

/**
 *
 * @author BetaSteward
 */
public class CardState implements Serializable, Copyable<CardState> {

    protected boolean faceDown;
    protected Map<String, String> info = new LinkedHashMap<>(); // additional info for card's rules
    protected Counters counters;
    protected Abilities<Ability> abilities;
    protected boolean lostAllAbilities;
    protected boolean melded;

    private static final Map<String, String> emptyInfo = new HashMap<>();
    private static final Abilities<Ability> emptyAbilities = new AbilitiesImpl<>();

    public CardState() {
        counters = new Counters();
    }

    public CardState(final CardState state) {
        this.faceDown = state.faceDown;
        this.info.putAll(state.info);
        counters = state.counters.copy();
        if (state.abilities != null) {
            abilities = new AbilitiesImpl<>();
            for (Ability ability : state.abilities) {
                abilities.add(ability.copy());
            }
        }
        this.lostAllAbilities = state.lostAllAbilities;
        this.melded = state.melded;
    }

    @Override
    public CardState copy() {
        return new CardState(this);
    }

    public void setFaceDown(boolean value) {
        faceDown = value;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public Counters getCounters() {
        return counters;
    }

    public void addInfo(String key, String value) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }

    public Map<String, String> getInfo() {
        if (info == null) {
            return emptyInfo;
        }
        return info;
    }

    public Abilities<Ability> getAbilities() {
        if (abilities == null) {
            return emptyAbilities;
        }
        return abilities;
    }

    public void addAbility(Ability ability) {
        if (abilities == null) {
            abilities = new AbilitiesImpl<>();
        }
        abilities.add(ability);
        abilities.addAll(ability.getSubAbilities());
    }

    /**
     * Called from applyEffects reset, to reset all layered effects
     */
    public void clearAbilities() {
        if (abilities != null) {
            abilities = null;
        }
        setLostAllAbilities(false);
    }

    public void clear() {
        counters.clear();
        info.clear();
        clearAbilities();
        lostAllAbilities = false;
    }

    public boolean hasLostAllAbilities() {
        return lostAllAbilities;
    }

    public void setLostAllAbilities(boolean lostAllAbilities) {
        this.lostAllAbilities = lostAllAbilities;
    }

    public boolean isMelded() {
        return melded;
    }

    public void setMelded(boolean melded) {
        this.melded = melded;
    }

    @Override
    public String toString() {
        List<String> info = new ArrayList<>();

        if (this.faceDown) {
            info.add("face down");
        }
        if (this.counters != null && !this.counters.isEmpty()) {
            info.add("counters: " + this.counters.values().stream().mapToInt(Counter::getCount).sum());
        }
        if (this.abilities != null && !this.abilities.isEmpty()) {
            info.add("abilities: " + abilities.size());
        }
        if (this.lostAllAbilities) {
            info.add("lost all");
        }
        if (this.melded) {
            info.add("melded");
        }

        if (info.isEmpty()) {
            return "";
        } else {
            return String.join("; ", info);
        }
    }
}
