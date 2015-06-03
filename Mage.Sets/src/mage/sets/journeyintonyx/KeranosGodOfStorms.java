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
package mage.sets.journeyintonyx;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.Watcher;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author LevelX2
 */
public class KeranosGodOfStorms extends CardImpl {

    
    public KeranosGodOfStorms(UUID ownerId) {
        super(ownerId, 151, "Keranos, God of Storms", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{R}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to blue and red is less than seven, Keranos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.U, ColoredManaSymbol.R), 7);
        effect.setText("As long as your devotion to blue and red is less than seven, Keranos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.
        this.addAbility(new KeranosGodOfStormsTriggeredAbility(), new CardsDrawnDuringTurnWatcher());
        
        
    }

    public KeranosGodOfStorms(final KeranosGodOfStorms card) {
        super(card);
    }

    @Override
    public KeranosGodOfStorms copy() {
        return new KeranosGodOfStorms(this);
    }
}

class KeranosGodOfStormsTriggeredAbility extends TriggeredAbilityImpl {

    KeranosGodOfStormsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect(""), false);
    }

    KeranosGodOfStormsTriggeredAbility(final KeranosGodOfStormsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KeranosGodOfStormsTriggeredAbility copy() {
        return new KeranosGodOfStormsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && event.getPlayerId().equals(this.getControllerId())) {
            if (game.getActivePlayerId().equals(this.getControllerId())) {
                CardsDrawnDuringTurnWatcher watcher = (CardsDrawnDuringTurnWatcher) game.getState().getWatchers().get("CardsDrawnDuringTurn");
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) != 1) {
                    return false;
                }
                Card card = game.getCard(event.getTargetId());
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourcePermanent = (Permanent) getSourceObject(game);
                if (card != null && controller != null && sourcePermanent != null) {
                    controller.revealCards(sourcePermanent.getIdName(), new CardsImpl(card), game);
                    this.getTargets().clear();
                    this.getEffects().clear();
                    if (card.getCardType().contains(CardType.LAND)) {
                        this.addEffect(new DrawCardSourceControllerEffect(1));
                    } else {
                        this.addEffect(new DamageTargetEffect(3));
                        this.addTarget(new TargetCreatureOrPlayer());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw on each of your turns. Whenever you reveal a land card this way, draw a card. Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.";
    }
}

class CardsDrawnDuringTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<>();

    public CardsDrawnDuringTurnWatcher() {
        super("CardsDrawnDuringTurn", WatcherScope.GAME);
    }

    public CardsDrawnDuringTurnWatcher(final CardsDrawnDuringTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDrawnThisTurn.entrySet()) {
            amountOfCardsDrawnThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
                if (amount == null) {
                    amount = 1;
                } else {
                    amount++;
                }
                amountOfCardsDrawnThisTurn.put(playerId, amount);
            }
        }
    }

    public int getAmountCardsDrawn(UUID playerId) {
        Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    @Override
    public void reset() {
        amountOfCardsDrawnThisTurn.clear();
    }

    @Override
    public CardsDrawnDuringTurnWatcher copy() {
        return new CardsDrawnDuringTurnWatcher(this);
    }
}