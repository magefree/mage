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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class BaneOfProgress extends CardImpl<BaneOfProgress> {

    public BaneOfProgress(UUID ownerId) {
        super(ownerId, 137, "Bane of Progress", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "C13";
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Bane of Progress enters the battlefield, destroy all artifacts and enchantments. Put a +1/+1 counter on Bane of Progress for each permanent destroyed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BaneOfProgressEffect(), false));
    }

    public BaneOfProgress(final BaneOfProgress card) {
        super(card);
    }

    @Override
    public BaneOfProgress copy() {
        return new BaneOfProgress(this);
    }
}

class BaneOfProgressEffect extends OneShotEffect<BaneOfProgressEffect> {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    public BaneOfProgressEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all artifacts and enchantments. Put a +1/+1 counter on {this} for each permanent destroyed this way";
    }

    public BaneOfProgressEffect(final BaneOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public BaneOfProgressEffect copy() {
        return new BaneOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int destroyedPermanents = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (permanent.destroy(source.getSourceId(), game, false)) {
                destroyedPermanents++;
            }
        }
        if (destroyedPermanents > 0) {
            return new AddCountersSourceEffect(CounterType.P1P1.createInstance(destroyedPermanents),true).apply(game, source);
        }
        return true;
    }
}
