package mage.abilities.keyword;

import mage.MageObject;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * Hexproof (This creature or player can't be the target of spells or abilities
 * your opponents control.)
 *
 * @author loki
 */
public class HexproofAbility extends HexproofBaseAbility {

    private static final HexproofAbility instance;

    static {
        instance = new HexproofAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofAbility getInstance() {
        return instance;
    }

    private HexproofAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject source, Game game) {
        return true;
    }

    @Override
    public HexproofAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from all";
    }
}
