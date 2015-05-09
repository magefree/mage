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
package mage.sets.onslaught;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class AstralSlide extends CardImpl {

    public AstralSlide(UUID ownerId) {
        super(ownerId, 4, "Astral Slide", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "ONS";

        this.color.setWhite(true);

        // Whenever a player cycles a card, you may exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new CycleAllTriggeredAbility(new AstralSlideEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public AstralSlide(final AstralSlide card) {
        super(card);
    }

    @Override
    public AstralSlide copy() {
        return new AstralSlide(this);
    }
}


class AstralSlideEffect extends OneShotEffect {

    public AstralSlideEffect() {
        super(Outcome.Detriment);
        staticText = "exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public AstralSlideEffect(final AstralSlideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                UUID exileId = UUID.randomUUID();
                if (controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                    //create delayed triggered ability
                    AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(exileId, Zone.BATTLEFIELD, false));
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    delayedAbility.setSourceObject(source.getSourceObject(game), game);
                    game.addDelayedTriggeredAbility(delayedAbility);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public AstralSlideEffect copy() {
        return new AstralSlideEffect(this);
    }
}
