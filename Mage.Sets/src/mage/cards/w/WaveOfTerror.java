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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class WaveOfTerror extends CardImpl {

    public WaveOfTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));

        // At the beginning of your draw step, destroy each creature with converted mana cost equal to the number of age counters on Wave of Terror. They can't be regenerated.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new WaveOfTerrorEffect(), TargetController.YOU, false));
    }

    public WaveOfTerror(final WaveOfTerror card) {
        super(card);
    }

    @Override
    public WaveOfTerror copy() {
        return new WaveOfTerror(this);
    }
}

class WaveOfTerrorEffect extends OneShotEffect {

    WaveOfTerrorEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy each creature with converted mana cost equal to the number of age counters on {this}. They can't be regenerated.";
    }

    WaveOfTerrorEffect(final WaveOfTerrorEffect effect) {
        super(effect);
    }

    @Override
    public WaveOfTerrorEffect copy() {
        return new WaveOfTerrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ConvertedManaCostPredicate(
                ComparisonType.EQUAL_TO,
                permanent.getCounters(game).getCount(CounterType.AGE)
        ));
        return new DestroyAllEffect(filter, true).apply(game, source);
    }
}
