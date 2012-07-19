package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantTargetSourceEffect;
import mage.filter.FilterStackObject;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.io.ObjectStreamException;

/**
 * Hexproof
 * (This creature or player can't be the target of spells or abilities your opponents control.)
 *
 * @author loki
 */
public class HexproofAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofAbility fINSTANCE;
    private static final FilterStackObject filter;

    static {
        filter = new FilterStackObject("spells or abilities your opponents control");
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
        fINSTANCE = new HexproofAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static HexproofAbility getInstance() {
        return fINSTANCE;
    }

    private HexproofAbility() {
        super(Constants.Zone.BATTLEFIELD, new CantTargetSourceEffect(filter, Constants.Duration.WhileOnBattlefield));
    }

    @Override
    public HexproofAbility copy() {
        return fINSTANCE;
    }

    @Override
    public String getRule() {
        return "Hexproof";
    }
}
