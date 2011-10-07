package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.Constants;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantTargetSourceEffect;
import mage.filter.FilterStackObject;

/**
 * Hexproof
 * (This creature or player can't be the target of spells or abilities your opponents control.)
 *
 * @author loki
 */
public class HexproofAbility extends SimpleStaticAbility {

	private static final HexproofAbility fINSTANCE =  new HexproofAbility();
 
	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static HexproofAbility getInstance() {
		return fINSTANCE;
	}

   private HexproofAbility() {
        super(Constants.Zone.BATTLEFIELD, new CantTargetSourceEffect(new FilterStackObject("spells or abilities your opponents control"), Constants.Duration.WhileOnBattlefield));
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
