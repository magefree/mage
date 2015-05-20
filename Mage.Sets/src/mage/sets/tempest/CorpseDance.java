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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CorpseDance extends CardImpl {

    public CorpseDance(UUID ownerId) {
        super(ownerId, 10, "Corpse Dance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "TMP";


        // Buyback {2}
        this.addAbility(new BuybackAbility("{2}"));

        // Return the top creature card of your graveyard to the battlefield. That creature gains haste until end of turn. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new CorpseDanceEffect());
    }

    public CorpseDance(final CorpseDance card) {
        super(card);
    }

    @Override
    public CorpseDance copy() {
        return new CorpseDance(this);
    }
}

class CorpseDanceEffect extends OneShotEffect {

    public CorpseDanceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return the top creature card of your graveyard to the battlefield. That creature gains haste until end of turn. Exile it at the beginning of the next end step";
    }

    public CorpseDanceEffect(final CorpseDanceEffect effect) {
        super(effect);
    }

    @Override
    public CorpseDanceEffect copy() {
        return new CorpseDanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card lastCreatureCard = null;
            for (Card card :controller.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    lastCreatureCard = card;
                }
            }
            if (lastCreatureCard != null) {
                if (controller.putOntoBattlefieldWithInfo(lastCreatureCard, game, Zone.GRAVEYARD, source.getSourceId())) {
                    FixedTarget fixedTarget = new FixedTarget(lastCreatureCard.getId());
                    // Gains Haste
                    ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    hasteEffect.setTargetPointer(fixedTarget);
                    game.addEffect(hasteEffect, source);
                    // Exile it at end of turn
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null,"",Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(fixedTarget);
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    delayedAbility.setSourceObject(source.getSourceObject(game), game);
                    game.addDelayedTriggeredAbility(delayedAbility);
                }
            }
            return true;
        }
        return false;
    }
}
