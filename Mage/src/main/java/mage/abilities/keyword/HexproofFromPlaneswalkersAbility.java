package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from planesalkers
 *
 * @author weirddan455
 */
public class HexproofFromPlaneswalkersAbility extends HexproofBaseAbility {

    private static final HexproofFromPlaneswalkersAbility instance;

    static {
        instance = new HexproofFromPlaneswalkersAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromPlaneswalkersAbility getInstance() {
        return instance;
    }

    private HexproofFromPlaneswalkersAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return source.isPlaneswalker(game);
    }

    @Override
    public HexproofFromPlaneswalkersAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from planeswalkers";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from planeswalkers";
    }
}
