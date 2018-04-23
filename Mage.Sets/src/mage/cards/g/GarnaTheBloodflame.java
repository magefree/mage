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
package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class GarnaTheBloodflame extends CardImpl {

    public GarnaTheBloodflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Garna, the Bloodflame enters the battlefield, return to your hand all creature cards in your graveyard that were put there from anywhere this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GarnaTheBloodflameEffect(), false), new GarnaTheBloodflameWatcher());

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));
    }

    public GarnaTheBloodflame(final GarnaTheBloodflame card) {
        super(card);
    }

    @Override
    public GarnaTheBloodflame copy() {
        return new GarnaTheBloodflame(this);
    }
}

class GarnaTheBloodflameEffect extends OneShotEffect {

    GarnaTheBloodflameEffect() {
        super(Outcome.Benefit);
        staticText = "return to your hand all creature cards in your graveyard that were put there from anywhere this turn";
    }

    GarnaTheBloodflameEffect(final GarnaTheBloodflameEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            GarnaTheBloodflameWatcher watcher = (GarnaTheBloodflameWatcher) game.getState().getWatchers().get(GarnaTheBloodflameWatcher.class.getSimpleName());
            if (watcher != null) {
                Set<Card> toHand = new HashSet<>();
                for (UUID cardId : watcher.getCardsPutToGraveyardThisTurn()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getOwnerId().equals(source.getControllerId()) && game.getState().getZone(cardId) == Zone.GRAVEYARD) {
                        toHand.add(card);
                    }
                }
                if (!toHand.isEmpty()) {
                    controller.moveCards(toHand, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public GarnaTheBloodflameEffect copy() {
        return new GarnaTheBloodflameEffect(this);
    }
}

class GarnaTheBloodflameWatcher extends Watcher {

    private final Set<UUID> cards = new HashSet<>();

    public GarnaTheBloodflameWatcher() {
        super(GarnaTheBloodflameWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public GarnaTheBloodflameWatcher(final GarnaTheBloodflameWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone().equals(Zone.GRAVEYARD)) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature()) {
                cards.add(event.getTargetId());
            }
        }
    }

    @Override
    public GarnaTheBloodflameWatcher copy() {
        return new GarnaTheBloodflameWatcher(this);
    }

    public Set<UUID> getCardsPutToGraveyardThisTurn() {
        return cards;
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
