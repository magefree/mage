/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.filter.common;

import mage.Constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreaturePermanent extends FilterPermanent {

    protected boolean useDamageDealt;
    protected boolean damageDealt;

    public FilterCreaturePermanent() {
        this("creature");
    }

    public FilterCreaturePermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.CREATURE));
    }

    public FilterCreaturePermanent(final FilterCreaturePermanent filter) {
        super(filter);
        this.useDamageDealt = filter.useDamageDealt;
        this.damageDealt = filter.damageDealt;
    }

    @Override
    public boolean match(Permanent permanent, Game game) {
        if (!super.match(permanent, game))
            return notFilter;

        if (useDamageDealt) {
            // use this instead of getDamage() because damage is reset in case of regeneration
            if (permanent.getDealtDamageByThisTurn().isEmpty()) {
                return notFilter;
            }
        }
        return !notFilter;
    }

    /**
     * Select creatures dependant if they already got damage during the current turn. Works also if the creature was
     * meanwhile regenerated during the turn.
     *
     * @param useDamageDealt
     */
    public void setUseDamageDealt(boolean useDamageDealt) {
        this.useDamageDealt = useDamageDealt;
    }

    /**
     * Select creatures that got damage dealt to this turn.
     *
     * @param damageDealt
     */
    public void setDamageDealt(boolean damageDealt) {
        this.damageDealt = damageDealt;
    }

    @Override
    public FilterCreaturePermanent copy() {
        return new FilterCreaturePermanent(this);
    }
}
