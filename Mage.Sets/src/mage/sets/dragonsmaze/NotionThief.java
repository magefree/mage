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
package mage.sets.dragonsmaze;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class NotionThief extends CardImpl<NotionThief> {

    public NotionThief(UUID ownerId) {
        super(ownerId, 88, "Notion Thief", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If an opponent would draw a card except the first one he or she draws in each of his or her draw steps, instead that player skips that draw and you draw a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NotionThiefReplacementEffect()));
        this.addWatcher(new NotionThiefDrawWatcher());

    }

    public NotionThief(final NotionThief card) {
        super(card);
    }

    @Override
    public NotionThief copy() {
        return new NotionThief(this);
    }
}


class NotionThiefReplacementEffect extends ReplacementEffectImpl<NotionThiefReplacementEffect> {

    public NotionThiefReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would draw a card except the first one he or she draws in each of his or her draw steps, instead that player skips that draw and you draw a card";
    }

    public NotionThiefReplacementEffect(final NotionThiefReplacementEffect effect) {
        super(effect);
    }

    @Override
    public NotionThiefReplacementEffect copy() {
        return new NotionThiefReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, game, event.getAppliedEffects());
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DRAW_CARD && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            NotionThiefDrawWatcher watcher = (NotionThiefDrawWatcher) game.getState().getWatchers().get("NotionThiefDrawWatcher");
            if (!game.getPhase().getStep().getType().equals(PhaseStep.DRAW)
                    || watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                return true;
            }
        }
        return false;
    }
}

/*
 * counts cards drawn during draw step
 */
class NotionThiefDrawWatcher extends WatcherImpl<NotionThiefDrawWatcher> {

    private Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<UUID, Integer>();

    public NotionThiefDrawWatcher() {
        super("NotionThiefDrawWatcher", WatcherScope.GAME);
    }

    public NotionThiefDrawWatcher(final NotionThiefDrawWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDrawnThisTurn.entrySet()) {
            amountOfCardsDrawnThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && game.getPhase() != null
                && game.getPhase().getStep().getType().equals(PhaseStep.DRAW)) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
                if (amount == null) {
                    amount = Integer.valueOf(1);
                } else {
                    amount = Integer.valueOf(amount + 1);
                }
                amountOfCardsDrawnThisTurn.put(playerId, amount);
            }
        }
    }

    public int getAmountCardsDrawn(UUID playerId) {
        Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
        if (amount != null) {
            return amount.intValue();
        }
        return 0;
    }

    @Override
    public void reset() {
        amountOfCardsDrawnThisTurn.clear();
    }

    @Override
    public NotionThiefDrawWatcher copy() {
        return new NotionThiefDrawWatcher(this);
    }
}
