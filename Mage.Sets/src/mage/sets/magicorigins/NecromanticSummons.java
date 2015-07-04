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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class NecromanticSummons extends CardImpl {

    public NecromanticSummons(UUID ownerId) {
        super(ownerId, 110, "Necromantic Summons", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{B}");
        this.expansionSetCode = "ORI";

        // Put target creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        
        // <i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, that creature enters the battlefield with two additional +1/+1 counters on it.
        Effect effect = new ConditionalOneShotEffect(new NecromanticSummoningEffect(),
                SpellMasteryCondition.getInstance(), "<br><i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, add {B}{B}{B} to your mana pool");
        this.getSpellAbility().addEffect(effect);
    }

    public NecromanticSummons(final NecromanticSummons card) {
        super(card);
    }

    @Override
    public NecromanticSummons copy() {
        return new NecromanticSummons(this);
    }
}

class NecromanticSummoningEffect extends OneShotEffect {

    public NecromanticSummoningEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "<br><i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, that creature enters the battlefield with two additional +1/+1 counters on it";
    }

    public NecromanticSummoningEffect(final NecromanticSummoningEffect effect) {
        super(effect);
    }

    @Override
    public NecromanticSummoningEffect copy() {
        return new NecromanticSummoningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), game);
            return true;
        }
        return false;
    }
}