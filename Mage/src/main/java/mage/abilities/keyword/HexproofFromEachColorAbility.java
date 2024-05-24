package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from each color
 *
 * @author TheElk801
 */
public class HexproofFromEachColorAbility extends HexproofBaseAbility {

    private static final HexproofFromEachColorAbility instance;

    static {
        instance = new HexproofFromEachColorAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromEachColorAbility getInstance() {
        return instance;
    }

    private HexproofFromEachColorAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return !source.getColor(game).isColorless();
    }

    @Override
    public HexproofFromEachColorAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from each color";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from each color";
    }
}
