/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.common;

import java.io.ObjectStreamException;
import mage.Constants.Duration;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author matthew.maurer
 */
public class CantBlockAbility extends SimpleStaticAbility {

	private static final CantBlockAbility fINSTANCE =  new CantBlockAbility();

	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static CantBlockAbility getInstance() {
		return fINSTANCE;
	}

	private CantBlockAbility() {
		super(Zone.BATTLEFIELD, new CantBlockEffect());
	}

	@Override
	public String getRule() {
		return "{this} can't block";
	}

	@Override
	public CantBlockAbility copy() {
		return fINSTANCE;
	}

}

class CantBlockEffect extends RestrictionEffect<CantBlockEffect> {

	public CantBlockEffect() {
		super(Duration.WhileOnBattlefield);
	}

	public CantBlockEffect(final CantBlockEffect effect) {
		super(effect);
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		if (permanent.getAbilities().containsKey(CantBlockAbility.getInstance().getId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canBlock(Permanent blocker, Game game) {
		if (blocker.getAbilities().containsKey(CantBlockAbility.getInstance().getId())) {
			return false;
		}
		return true;
	}

	@Override
	public CantBlockEffect copy() {
		return new CantBlockEffect(this);
	}

}
