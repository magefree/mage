package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from red (This creature or player can't be the target of red
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromRedAbility extends HexproofBaseAbility {

    private static final HexproofFromRedAbility instance;

    static {
        instance = new HexproofFromRedAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromRedAbility getInstance() {
        return instance;
    }

    private HexproofFromRedAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return sourceObject.getColor(game).isRed();
    }

    @Override
    public HexproofFromRedAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from red <i>(This creature can't be the target of red spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from red";
    }
}
