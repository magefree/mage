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
package mage.sets.alliances;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public class Burnout extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant spell");

    static {
            filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public Burnout(UUID ownerId) {
        super(ownerId, 101, "Burnout", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ALL";

        // Counter target instant spell if it's blue.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new BurnoutCounterTargetEffect());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false));
    }

    public Burnout(final Burnout card) {
        super(card);
    }

    @Override
    public Burnout copy() {
        return new Burnout(this);
    }
}

class BurnoutCounterTargetEffect extends OneShotEffect {

    public BurnoutCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public BurnoutCounterTargetEffect(final BurnoutCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public BurnoutCounterTargetEffect copy() {
        return new BurnoutCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if(game.getStack().getSpell(source.getFirstTarget()).getColor(game).isBlue()){
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return true;
    }

    public String getText(Ability source) {
        return "Counter target instant spell if it's blue";
    }
}