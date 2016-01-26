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
package mage.sets.commander2014;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public class WakeTheDead extends CardImpl {

    public WakeTheDead(UUID ownerId) {
        super(ownerId, 31, "Wake the Dead", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.expansionSetCode = "C14";

        // Cast Wake the Dead only during combat on an opponent's turn.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, OnOpponentsTurnCondition.getInstance()));

        // Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step.
        this.getSpellAbility().addEffect(new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInYourGraveyard(xValue, xValue, new FilterCreatureCard("creature cards from your graveyard")));
        }
    }

    public WakeTheDead(final WakeTheDead card) {
        super(card);
    }

    @Override
    public WakeTheDead copy() {
        return new WakeTheDead(this);
    }
}

class WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step";
    }

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(final WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
    }

    @Override
    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
            controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
            ArrayList<Permanent> toSacrifice = new ArrayList<>(cards.size());
            for (UUID targetId : cards) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    toSacrifice.add(creature);
                }

            }
            Effect effect = new SacrificeTargetEffect("Sacrifice those creatures at the beginning of the next end step", source.getControllerId());
            effect.setTargetPointer(new FixedTargets(toSacrifice, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

}
