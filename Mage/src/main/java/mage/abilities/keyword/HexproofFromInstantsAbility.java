package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from instants
 *
 * @author ciaccona007
 */
public class HexproofFromInstantsAbility extends HexproofBaseAbility {

    private static final HexproofFromInstantsAbility instance;

    static {
        instance = new HexproofFromInstantsAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromInstantsAbility getInstance() {
        return instance;
    }

    private HexproofFromInstantsAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return sourceObject.isInstant(game);
    }

    @Override
    public HexproofFromInstantsAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from instants";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from instants";
    }
}
