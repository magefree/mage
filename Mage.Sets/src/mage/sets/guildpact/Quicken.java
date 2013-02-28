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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author LevelX2
 *
 * TODO: Change to handling with CAST_SPELL watcher. All existing effects must be consumed if a sorcery is cast.
 */
public class Quicken extends CardImpl<Quicken> {

    public Quicken(UUID ownerId) {
        super(ownerId, 31, "Quicken", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "GPT";

        this.color.setBlue(true);

        // The next sorcery card you cast this turn can be cast as though it had flash.
        this.getSpellAbility().addEffect(new QuickenAsThoughEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
    }

    public Quicken(final Quicken card) {
        super(card);
    }

    @Override
    public Quicken copy() {
        return new Quicken(this);
    }
}

class QuickenAsThoughEffect extends AsThoughEffectImpl<QuickenAsThoughEffect> {
    private UUID cardId;
    private int zoneChangeCounter;

    public QuickenAsThoughEffect() {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next sorcery card you cast this turn can be cast as though it had flash";
    }

    public QuickenAsThoughEffect(final QuickenAsThoughEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuickenAsThoughEffect copy() {
        return new QuickenAsThoughEffect(this);
    }


    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && (cardId == null || (cardId.equals(sourceId) && zoneChangeCounter == card.getZoneChangeCounter()))) {
            if (card.getCardType().contains(CardType.SORCERY) && card.getOwnerId().equals(source.getControllerId())) {
                if (card.getSpellAbility().isInUseableZone(game, card, false)) {
                    if (cardId == null) {
                        cardId = card.getId();
                        zoneChangeCounter = card.getZoneChangeCounter();
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
