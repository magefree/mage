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
package mage.abilities.keyword;

import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * If you would draw a card, instead you may put exactly X cards from the top of your library into your graveyard. If
 * you do, return this card from your graveyard to your hand. Otherwise, draw a card.
 *
 * @author North
 */
public class DredgeAbility extends SimpleStaticAbility {

    public DredgeAbility(int value) {
        super(Zone.GRAVEYARD, new DredgeEffect(value));
    }

    public DredgeAbility(final DredgeAbility ability) {
        super(ability);
    }

    @Override
    public DredgeAbility copy() {
        return new DredgeAbility(this);
    }
}

class DredgeEffect extends ReplacementEffectImpl<DredgeEffect> {

    private int amount;

    public DredgeEffect(int value) {
        super(Duration.WhileInGraveyard, Outcome.ReturnToHand);
        this.amount = value;
        this.staticText = new StringBuilder("Dredge ").append(Integer.toString(value))
                .append(" <i>(If you would draw a card, instead you may put exactly three cards from the top of your library into your graveyard. If you do, return this card from your graveyard to your hand. Otherwise, draw a card.)</i>").toString();
    }

    public DredgeEffect(final DredgeEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DredgeEffect copy() {
        return new DredgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() >= amount 
                && player.chooseUse(outcome, new StringBuilder("Dredge ").append(sourceCard.getName()).
                append(" (").append(amount).append(" cards go from top of library to graveyard)").toString(), game)) {
            for (int i = 0; i < amount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
                }
            }
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DRAW_CARD) && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}
