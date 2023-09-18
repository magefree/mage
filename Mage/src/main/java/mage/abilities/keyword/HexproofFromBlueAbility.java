package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from blue (This creature or player can't be the target of black
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromBlueAbility extends HexproofBaseAbility {

    private static final HexproofFromBlueAbility instance;

    static {
        instance = new HexproofFromBlueAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromBlueAbility getInstance() {
        return instance;
    }

    private HexproofFromBlueAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return source.getColor(game).isBlue();
    }

    @Override
    public HexproofFromBlueAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from blue <i>(This creature can't be the target of blue spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from blue";
    }
}
