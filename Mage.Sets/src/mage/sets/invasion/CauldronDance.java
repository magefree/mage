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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nomage
 */
public class CauldronDance extends CardImpl {

    public CauldronDance(UUID ownerId) {
        super(ownerId, 238, "Cauldron Dance", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{B}{R}");
        this.expansionSetCode = "INV";

        // Cast Cauldron Dance only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Return target creature card from your graveyard to the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step.
        this.getSpellAbility().addEffect(new CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));

        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Its controller sacrifices it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new CauldronDancePutCreatureFromHandOntoBattlefieldEffect());
    }

    public CauldronDance(final CauldronDance card) {
        super(card);
    }

    @Override
    public CauldronDance copy() {
        return new CauldronDance(this);
    }
}

class CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    public CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step";
    }

    public CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect(final CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
    }

    @Override
    public CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new CauldronDanceReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = getTargetPointer().getFirst(game, source);
            Card card = game.getCard(targetId);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                Permanent creature = game.getPermanent(card.getId());
                if (creature != null) {
                    ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    hasteEffect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(hasteEffect, source);

                    ReturnToHandTargetEffect returnToHandEffect = new ReturnToHandTargetEffect();
                    returnToHandEffect.setText("return that creature to your hand");
                    returnToHandEffect.setTargetPointer(new FixedTarget(creature, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}

class CauldronDancePutCreatureFromHandOntoBattlefieldEffect extends OneShotEffect {

    private static final String CHOICE_TEXT = "Put a creature card from your hand onto the battlefield?";

    public CauldronDancePutCreatureFromHandOntoBattlefieldEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card from your hand onto the battlefield. That creature gains haste. Its controller sacrifices it at the beginning of the next end step";
    }

    public CauldronDancePutCreatureFromHandOntoBattlefieldEffect(final CauldronDancePutCreatureFromHandOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public CauldronDancePutCreatureFromHandOntoBattlefieldEffect copy() {
        return new CauldronDancePutCreatureFromHandOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, CHOICE_TEXT, source, game)) {
                TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
                if (controller.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addEffect(effect, source);

                                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + card.getName(), source.getControllerId());
                                sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                                game.addDelayedTriggeredAbility(delayedAbility, source);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
