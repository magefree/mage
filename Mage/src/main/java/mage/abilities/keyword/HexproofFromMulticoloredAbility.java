package mage.abilities.keyword;

import java.io.ObjectStreamException;

import mage.MageObject;
import mage.game.Game;

public class HexproofFromMulticoloredAbility extends HexproofBaseAbility {

    private static final HexproofFromMulticoloredAbility instance;

    static {
        instance = new HexproofFromMulticoloredAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromMulticoloredAbility getInstance() {
        return instance;
    }

    private HexproofFromMulticoloredAbility() {
        super();
    }

    @Override
    public HexproofFromMulticoloredAbility copy() {
        return instance;
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return source.getColor().isMulticolored();
    }

    @Override
    public String getRule() {
        return "hexproof from multicolored <i>(This creature can't be the target of multicolored spells or abilities your opponents control.)</i>";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from multicolored";
    }
}
