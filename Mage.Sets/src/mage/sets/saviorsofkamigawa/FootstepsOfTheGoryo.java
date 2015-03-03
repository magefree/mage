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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
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
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public class FootstepsOfTheGoryo extends CardImpl {

    public FootstepsOfTheGoryo(UUID ownerId) {
        super(ownerId, 68, "Footsteps of the Goryo", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Arcane");

        this.color.setBlack(true);

        // Return target creature card from your graveyard to the battlefield. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new FootstepsOfTheGoryoEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
    }

    public FootstepsOfTheGoryo(final FootstepsOfTheGoryo card) {
        super(card);
    }

    @Override
    public FootstepsOfTheGoryo copy() {
        return new FootstepsOfTheGoryo(this);
    }
}

class FootstepsOfTheGoryoEffect extends OneShotEffect {

    public FootstepsOfTheGoryoEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. Sacrifice that creature at the beginning of the next end step";
    }

    public FootstepsOfTheGoryoEffect(final FootstepsOfTheGoryoEffect effect) {
        super(effect);
    }

    @Override
    public FootstepsOfTheGoryoEffect copy() {
        return new FootstepsOfTheGoryoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId())) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        // Sacrifice it at end of turn
                        Effect sacrificeEffect = new SacrificeTargetEffect("Sacrifice that creature at the beginning of next end step");
                        sacrificeEffect.setTargetPointer(new FixedTarget(card.getId()));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        delayedAbility.setSourceObject(source.getSourceObject(game));
                        game.addDelayedTriggeredAbility(delayedAbility);
                        return true;

                    }
                }
            }
        }
        return false;
    }
}
