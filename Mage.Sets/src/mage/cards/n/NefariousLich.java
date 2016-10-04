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
package mage.sets.odyssey;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public class NefariousLich extends CardImpl {

    public NefariousLich(UUID ownerId) {
        super(ownerId, 153, "Nefarious Lich", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}{B}");
        this.expansionSetCode = "ODY";

        // If damage would be dealt to you, exile that many cards from your graveyard instead. If you can't, you lose the game.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NefariousLichDamageReplacementEffect()));

        // If you would gain life, draw that many cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NefariousLichLifeGainReplacementEffect()));

        // When Nefarious Lich leaves the battlefield, you lose the game.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseGameSourceControllerEffect(), false));
    }

    public NefariousLich(final NefariousLich card) {
        super(card);
    }

    @Override
    public NefariousLich copy() {
        return new NefariousLich(this);
    }
}

class NefariousLichDamageReplacementEffect extends ReplacementEffectImpl {

    private int amount = 0;

    NefariousLichDamageReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If damage would be dealt to you, exile that many cards from your graveyard instead. If you can't, you lose the game.";
    }

    NefariousLichDamageReplacementEffect(final NefariousLichDamageReplacementEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public NefariousLichDamageReplacementEffect copy() {
        return new NefariousLichDamageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null) {
            Target target = new TargetCardInYourGraveyard(amount, new FilterCard("card in your graveyard"));
            if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                if (controller.choose(Outcome.Exile, target, source.getSourceId(), game)) {
                    Set<Card> cards = new HashSet<>(amount);
                    for (UUID targetId : target.getTargets()) {
                        Card card = controller.getGraveyard().get(targetId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                    controller.moveCardsToExile(cards, source, game, true, null, "");
                    return true;
                }
            }
            controller.lost(game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            this.amount = event.getAmount();
            return true;
        }
        return false;
    }
}

class NefariousLichLifeGainReplacementEffect extends ReplacementEffectImpl {

    NefariousLichLifeGainReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard);
        staticText = "If you would gain life, draw that many cards instead";
    }

    NefariousLichLifeGainReplacementEffect(final NefariousLichLifeGainReplacementEffect effect) {
        super(effect);
    }

    @Override
    public NefariousLichLifeGainReplacementEffect copy() {
        return new NefariousLichLifeGainReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null) {
            controller.drawCards(event.getAmount(), game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(EventType.GAIN_LIFE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
