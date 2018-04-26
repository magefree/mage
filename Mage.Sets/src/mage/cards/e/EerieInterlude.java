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
package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class EerieInterlude extends CardImpl {

    public EerieInterlude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Exile any number of target creatures you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new EerieInterludeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), false));

    }

    public EerieInterlude(final EerieInterlude card) {
        super(card);
    }

    @Override
    public EerieInterlude copy() {
        return new EerieInterlude(this);
    }
}

class EerieInterludeEffect extends OneShotEffect {

    public EerieInterludeEffect() {
        super(Outcome.Neutral);
        staticText = "Exile any number of target creatures you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    public EerieInterludeEffect(final EerieInterludeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Set<Card> toExile = new HashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent targetCreature = game.getPermanent(targetId);
                if (targetCreature != null) {
                    toExile.add(targetCreature);
                }
            }
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            controller.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());

            Cards cardsToReturn = new CardsImpl();
            for (Card exiled : toExile) {
                if (exiled instanceof PermanentMeld) {
                    MeldCard meldCard = (MeldCard) ((PermanentCard) exiled).getCard();
                    Card topCard = meldCard.getTopHalfCard();
                    Card bottomCard = meldCard.getBottomHalfCard();
                    if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter()) {
                        cardsToReturn.add(topCard);
                    }
                    if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter()) {
                        cardsToReturn.add(bottomCard);
                    }
                }
                else if (exiled.getZoneChangeCounter(game) == game.getState().getZoneChangeCounter(exiled.getId()) - 1) {
                    cardsToReturn.add(exiled);
                }
            }
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
            effect.setTargetPointer(new FixedTargets(cardsToReturn, game));
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

    @Override
    public EerieInterludeEffect copy() {
        return new EerieInterludeEffect(this);
    }
}
