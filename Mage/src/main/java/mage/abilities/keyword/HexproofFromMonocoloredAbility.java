package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof from Monocolored (This creature or player can't be the target of monocolored
 * spells or abilities your opponents control.)
 *
 * @author TheElk801
 */
public class HexproofFromMonocoloredAbility extends HexproofBaseAbility {

    private static final HexproofFromMonocoloredAbility instance;

    static {
        instance = new HexproofFromMonocoloredAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromMonocoloredAbility getInstance() {
        return instance;
    }

    private HexproofFromMonocoloredAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return !source.getColor(game).isMulticolored() && !source.getColor(game).isColorless();
    }

    @Override
    public HexproofFromMonocoloredAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from monocolored <i>(This creature can't be the target of monocolored spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from monocolored";
    }
}
