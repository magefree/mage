/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.common;

import mage.Constants.Duration;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class CantBlockAbility extends SimpleStaticAbility implements MageSingleton  {

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

class CantBlockEffect extends RestrictionEffect<CantBlockEffect> implements MageSingleton {

    public CantBlockEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public CantBlockEffect(final CantBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(CantBlockAbility.getInstance().getId()) || source.getId().equals(CantBlockAbility.getInstance().getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !blocker.getAbilities().containsKey(CantBlockAbility.getInstance().getId());
    }

    @Override
    public CantBlockEffect copy() {
        return new CantBlockEffect(this);
    }

}
