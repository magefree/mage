package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from white (This creature or player can't be the target of white
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromWhiteAbility extends HexproofBaseAbility {

    private static final HexproofFromWhiteAbility instance;

    static {
        instance = new HexproofFromWhiteAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromWhiteAbility getInstance() {
        return instance;
    }

    private HexproofFromWhiteAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return sourceObject.getColor(game).isWhite();
    }

    @Override
    public HexproofFromWhiteAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from white <i>(This creature can't be the target of white spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from white";
    }
}
