package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.ObjectStreamException;

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
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return sourceObject.getColor().isMulticolored();
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
