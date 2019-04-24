
package mage.cards.k;

import java.util.*;
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
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author spjspj
 */
public final class KydeleChosenOfKruphix extends CardImpl {

    public KydeleChosenOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add {C} for each card you've drawn this turn.
        DynamicManaAbility ability = new DynamicManaAbility(Mana.ColorlessMana(1), new CardsDrawnThisTurnDynamicValue(), new TapSourceCost());
        this.addAbility(ability, new KydeleCardsDrawnThisTurnWatcher());

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
        KydeleCardsDrawnThisTurnWatcher watcher = (KydeleCardsDrawnThisTurnWatcher) game.getState().getWatchers().get(KydeleCardsDrawnThisTurnWatcher.class.getSimpleName());
        return watcher.getCardsDrawnThisTurn(sourceAbility.getControllerId());
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
        return "card you've drawn this turn";
    }
}

class KydeleCardsDrawnThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> cardsDrawnThisTurn = new HashMap<>();

    public KydeleCardsDrawnThisTurnWatcher() {
        super(KydeleCardsDrawnThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public KydeleCardsDrawnThisTurnWatcher(final KydeleCardsDrawnThisTurnWatcher watcher) {
        super(watcher);
        this.cardsDrawnThisTurn.putAll(watcher.cardsDrawnThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            int cardsDrawn = getCardsDrawnThisTurn(event.getPlayerId());
            cardsDrawnThisTurn.put(event.getPlayerId(), cardsDrawn + 1);
        }
    }

    public int getCardsDrawnThisTurn(UUID playerId) {
        return cardsDrawnThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawnThisTurn.clear();

    }

    @Override
    public KydeleCardsDrawnThisTurnWatcher copy() {
        return new KydeleCardsDrawnThisTurnWatcher(this);
    }
}
