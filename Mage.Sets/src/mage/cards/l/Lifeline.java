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
package mage.cards.l;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author HCrescent
 */
public class Lifeline extends CardImpl {
    
private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature");

    public Lifeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
                

        // Whenever a creature dies, if another creature is on the battlefield, return the first card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new ConditionalTriggeredAbility(
                            new DiesCreatureTriggeredAbility(
                                new LifelineEffect(),
                                false,
                                false,
                                true),
                            new PermanentsOnTheBattlefieldCondition(filter, PermanentsOnTheBattlefieldCondition.CountType.MORE_THAN, 0, false),
                            "Whenever a creature dies, if another creature is on the battlefield, return the first card to the battlefield under its owner's control at the beginning of the next end step.");
        this.addAbility(ability);
    }

    public Lifeline(final Lifeline card) {
        super(card);
    }

    @Override
    public Lifeline copy() {
        return new Lifeline(this);
    }
}
class LifelineEffect extends OneShotEffect {
    
    public LifelineEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }
    
    public LifelineEffect(final LifelineEffect effect) {
        super(effect);
    }
    
    @Override
    public LifelineEffect copy() {
        return new LifelineEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        Permanent permanent = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            //Tracking zone changes after first effect
            permanent.setZoneChangeCounter(0, game);
            cards.add(permanent);
        }
        Effect effect = new LifelineDelayedEffect();
        effect.setValue("LifelineCards", cards);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }
}
class LifelineDelayedEffect extends OneShotEffect {
    
    public LifelineDelayedEffect() {
        super(Outcome.Benefit);
        this.staticText = "return this card to the battlefield under it's owner's control.";
    }
    
    public LifelineDelayedEffect(final LifelineDelayedEffect effect) {
        super(effect);
    }
    
    @Override
    public LifelineDelayedEffect copy() {
        return new LifelineDelayedEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = (Cards) this.getValue("LifelineCards");

        if (cards != null) {
            for (Iterator<Card> it = cards.getCards(game).iterator(); it.hasNext();) {
                Card card = it.next();
                // If the card changed zones Lifeline will no longer bring the card back
                if ( card != null && card.getZoneChangeCounter(game) == 0 ) {
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
