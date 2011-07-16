package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantTargetSourceEffect;
import mage.filter.FilterStackObject;

/**
 * Hexproof
 * (This creature can't be the target of spells or abilities your opponents control.)
 *
 * @author loki
 */
public class HexproofAbility extends SimpleStaticAbility {
    private static final FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

	static {
		filter.setTargetController(Constants.TargetController.OPPONENT);
	}

    public HexproofAbility() {
        super(Constants.Zone.BATTLEFIELD, new CantTargetSourceEffect(filter, Constants.Duration.WhileOnBattlefield));
    }

    public HexproofAbility(final HexproofAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new HexproofAbility(this);
    }

    @Override
    public String getRule() {
        return "Hexproof";
    }
}
