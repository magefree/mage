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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class Abeyance extends CardImpl {

    public Abeyance(UUID ownerId) {
        super(ownerId, 117, "Abeyance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "WTH";

        // Until end of turn, target player can't cast instant or sorcery spells, and that player can't activate abilities that aren't mana abilities.
        this.getSpellAbility().addEffect(new AbeyanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Abeyance(final Abeyance card) {
        super(card);
    }

    @Override
    public Abeyance copy() {
        return new Abeyance(this);
    }
}

class AbeyanceEffect extends ContinuousRuleModifyingEffectImpl {

    public AbeyanceEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Until end of turn, target player can't cast instant or sorcery spells, and that player can't activate abilities that aren't mana abilities";
    }

    public AbeyanceEffect(final AbeyanceEffect effect) {
        super(effect);
    }

    @Override
    public AbeyanceEffect copy() {
        return new AbeyanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells or activate abilities that aren't mana abilities this turn (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            MageObject object = game.getObject(event.getSourceId());
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                if (object.getCardType().contains(CardType.INSTANT) || object.getCardType().contains(CardType.SORCERY)) {
                    return true;
                }
            }
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
                if (ability != null && !(ability instanceof ManaAbility)) {
                    return true;
                }
            }
        }
        return false;
    }
}
