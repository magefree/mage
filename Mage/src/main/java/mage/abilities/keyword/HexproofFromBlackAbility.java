package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from black (This creature or player can't be the target of black
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromBlackAbility extends HexproofBaseAbility {

    private static final HexproofFromBlackAbility instance;

    static {
        instance = new HexproofFromBlackAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromBlackAbility getInstance() {
        return instance;
    }

    private HexproofFromBlackAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return source.getColor(game).isBlack();
    }

    @Override
    public HexproofFromBlackAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from black <i>(This creature can't be the target of black spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from black";
    }
}
