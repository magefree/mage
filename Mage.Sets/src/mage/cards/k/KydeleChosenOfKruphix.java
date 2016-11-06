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
package mage.cards.k;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author spjspj
 */
public class KydeleChosenOfKruphix extends CardImpl {

    public KydeleChosenOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add {C} to your mana pool for each card you've drawn this turn.
        DynamicManaAbility ability = new DynamicManaAbility(Mana.ColorlessMana(1), new CardsDrawnThisTurnDynamicValue(), new TapSourceCost());
        this.addAbility(ability, new CardsDrawnThisTurnWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public KydeleChosenOfKruphix(final KydeleChosenOfKruphix card) {
        super(card);
    }

    @Override
    public KydeleChosenOfKruphix copy() {
        return new KydeleChosenOfKruphix(this);
    }
}

class CardsDrawnThisTurnDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CardsDrawnThisTurnWatcher watcher = (CardsDrawnThisTurnWatcher) game.getState().getWatchers().get("CardsDrawnThisTurnWatcher");
        return watcher.getNumCardsDrawnThisTurn(sourceAbility.getControllerId());
    }

    @Override
    public CardsDrawnThisTurnDynamicValue copy() {
        return new CardsDrawnThisTurnDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "number of cards you've drawn this turn";
    }
}

class CardsDrawnThisTurnWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> cardsDrawnThisTurn = new HashMap<>();

    public CardsDrawnThisTurnWatcher() {
        super("CardsDrawnThisTurnWatcher", WatcherScope.GAME);
    }

    public CardsDrawnThisTurnWatcher(final CardsDrawnThisTurnWatcher watcher) {
        super(watcher);
        this.cardsDrawnThisTurn.putAll(watcher.cardsDrawnThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            if (!cardsDrawnThisTurn.containsKey(event.getPlayerId())) {
                Set<UUID> cardsDrawn = new LinkedHashSet<>();
                cardsDrawnThisTurn.put(event.getPlayerId(), cardsDrawn);
            }
            Set<UUID> cardsDrawn = cardsDrawnThisTurn.get(event.getPlayerId());
            cardsDrawn.add(event.getTargetId());
        }
    }

    private Set<UUID> getCardsDrawnThisTurn(UUID playerId) {
        return cardsDrawnThisTurn.get(playerId);
    }

    public int getNumCardsDrawnThisTurn(UUID playerId) {
        if (cardsDrawnThisTurn.get(playerId) == null) {
            return 0;
        }
        return cardsDrawnThisTurn.get(playerId).size();
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawnThisTurn.clear();
        
    }

    @Override
    public CardsDrawnThisTurnWatcher copy() {
        return new CardsDrawnThisTurnWatcher(this);
    }
}
