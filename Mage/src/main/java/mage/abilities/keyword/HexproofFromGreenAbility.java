package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from green (This creature or player can't be the target of green
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromGreenAbility extends HexproofBaseAbility {

    private static final HexproofFromGreenAbility instance;

    static {
        instance = new HexproofFromGreenAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromGreenAbility getInstance() {
        return instance;
    }

    private HexproofFromGreenAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return sourceObject.getColor(game).isGreen();
    }

    @Override
    public HexproofFromGreenAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from green <i>(This creature can't be the target of green spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from green";
    }
}
