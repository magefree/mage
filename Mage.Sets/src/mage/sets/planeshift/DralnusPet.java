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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LoneFox
 * @author LevelX
 */
public class DralnusPet extends CardImpl {

    public DralnusPet(UUID ownerId) {
        super(ownerId, 23, "Dralnu's Pet", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker-{2}{B}, Discard a creature card.
        Costs<Cost> kickerCosts = new CostsImpl<>();
        kickerCosts.add(new ManaCostsImpl<>("{2}{B}"));
        kickerCosts.add(new DiscardCardCost(new FilterCreatureCard()));
        this.addAbility(new KickerAbility(kickerCosts));
        // If Dralnu's Pet was kicked, it enters the battlefield with flying and with X +1/+1 counters on it, where X is the discarded card's converted mana cost.
        Ability ability = new EntersBattlefieldAbility(new DralnusPetEffect(), KickedCondition.getInstance(), true,
                "If {this} was kicked, it enters the battlefield with flying and with X +1/+1 counters on it, where X is the discarded card's converted mana cost", "");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    public DralnusPet(final DralnusPet card) {
        super(card);
    }

    @Override
    public DralnusPet copy() {
        return new DralnusPet(this);
    }
}

class DralnusPetEffect extends OneShotEffect {

    public DralnusPetEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "and with X +1/+1 counters on it, where X is the discarded card's converted mana cost";
    }

    public DralnusPetEffect(final DralnusPetEffect effect) {
        super(effect);
    }

    @Override
    public DralnusPetEffect copy() {
        return new DralnusPetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility
                    && permanent.getZoneChangeCounter(game) - 1 == ((SpellAbility) obj).getSourceObjectZoneChangeCounter()) {
                int cmc = 0;
                for (Cost cost : ((SpellAbility) obj).getCosts()) {
                    if (cost instanceof DiscardCardCost && ((DiscardCardCost) cost).getCards().size() > 0) {
                        cmc = ((DiscardCardCost) cost).getCards().get(0).getManaCost().convertedManaCost();
                    }
                    if (cmc > 0) {
                        return new AddCountersSourceEffect(CounterType.P1P1.createInstance(cmc), true).apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
