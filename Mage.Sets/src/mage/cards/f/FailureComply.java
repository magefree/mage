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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSpell;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.MageObject;
import mage.game.events.GameEvent.EventType;
import mage.abilities.effects.common.NameACardEffect;

import java.util.UUID;

/**
 * @author spjspj
 */
public class FailureComply extends SplitCard {

    public FailureComply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{U}", "{W}", false);

        // Failure
        // Return target spell to it's owner's hand
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // to
        // Comply
        // Choose a card name.  Until your next turn, your opponents can't cast spells with the chosen name
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addEffect(new NameACardEffect(NameACardEffect.TypeOfName.ALL));
        getRightHalfCard().getSpellAbility().addEffect(new ComplyCantCastEffect());
    }

    public FailureComply(final FailureComply card) {
        super(card);
    }

    @Override
    public FailureComply copy() {
        return new FailureComply(this);
    }
}

class ComplyCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public ComplyCantCastEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast spells with the chosen name";
    }

    public ComplyCantCastEffect(final ComplyCantCastEffect effect) {
        super(effect);
    }

    @Override
    public ComplyCantCastEffect copy() {
        return new ComplyCantCastEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
            return "You may not cast a card named " + cardName + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
