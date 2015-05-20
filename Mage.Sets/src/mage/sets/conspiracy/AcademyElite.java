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

package mage.sets.conspiracy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author andyfries
 */

public class AcademyElite extends CardImpl {
    public AcademyElite(UUID ownerId) {
        super(ownerId, 20, "Academy Elite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Academy Elite enters the battlefield with X +1/+1 counters on it, where X is the number of instant and
        // sorcery cards in all graveyards.
        this.addAbility(new EntersBattlefieldAbility(new AcademyEliteEffect1(), "with X +1/+1 counters on it, where X is the number of instant and sorcery cards in all graveyards"));

        // {2}{U}, Remove a +1/+1 counter from Academy Elite: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1, false), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(ability);
    }


    public AcademyElite(final AcademyElite card) {
        super(card);
    }

    @Override
    public AcademyElite copy() {
        return new AcademyElite(this);
    }

}

class AcademyEliteEffect1 extends OneShotEffect {

    public AcademyEliteEffect1() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with X +1/+1 counters on it, where X is the number of instant and sorcery cards in all graveyards";
    }

    public AcademyEliteEffect1(final AcademyEliteEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                CardsInAllGraveyardsCount instantsAndSorceries = new CardsInAllGraveyardsCount(new FilterInstantOrSorceryCard("instant or sorcery cards"));
                int instantsAndSorceriesCount = instantsAndSorceries.calculate(game, source, this);
                if (instantsAndSorceriesCount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(instantsAndSorceriesCount), game);
                }
            }
        }
        return true;
    }

    @Override
    public AcademyEliteEffect1 copy() {
        return new AcademyEliteEffect1(this);
    }
}

