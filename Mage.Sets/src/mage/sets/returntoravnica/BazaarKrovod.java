/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author jeffwadsworth
 */
public class BazaarKrovod extends CardImpl<BazaarKrovod> {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public BazaarKrovod(UUID ownerId) {
        super(ownerId, 7, "Bazaar Krovod", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Beast");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Bazaar Krovod attacks, another target attacking creature gets +0/+2 until end of turn. Untap that creature.
        Ability ability = new AttacksTriggeredAbility(new BazaarKrovodEffect(), false);
        ability.addTarget(new TargetAttackingCreature(1, 1, filter, false));
        this.addAbility(ability);
    }

    public BazaarKrovod(final BazaarKrovod card) {
        super(card);
    }

    @Override
    public BazaarKrovod copy() {
        return new BazaarKrovod(this);
    }
}

class BazaarKrovodEffect extends OneShotEffect<BazaarKrovodEffect> {

    public BazaarKrovodEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "another target attacking creature gets +0/+2 until end of turn. Untap that creature";
    }

    public BazaarKrovodEffect(BazaarKrovodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            game.addEffect(new BoostTargetEffect(0, 2, Constants.Duration.EndOfTurn), source);
            permanent.untap(game);
            return true;
        }
        return false;
    }

    @Override
    public BazaarKrovodEffect copy() {
        return new BazaarKrovodEffect(this);
    }

}
