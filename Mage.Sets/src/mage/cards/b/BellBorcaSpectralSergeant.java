package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BellBorcaSpectralSergeant extends CardImpl {

    public BellBorcaSpectralSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Note the converted mana cost of each card as it's put into exile.
        this.addAbility(BellBorcaSpectralSergeantAbility.getInstance());

        // Bell Borca, Spectral Sergeant's power is equal to the greatest number noted for it this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(
                BellBorcaSpectralSergeantValue.instance, Duration.EndOfGame
        ).setText("{this}'s power is equal to the greatest number noted for it this turn")));

        // At the beginning of your upkeep, exile the top card of your library. You may play that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), TargetController.YOU, false
        ), new BellBorcaSpectralSergeantWatcher());
    }

    private BellBorcaSpectralSergeant(final BellBorcaSpectralSergeant card) {
        super(card);
    }

    @Override
    public BellBorcaSpectralSergeant copy() {
        return new BellBorcaSpectralSergeant(this);
    }
}

class BellBorcaSpectralSergeantAbility extends StaticAbility implements MageSingleton {

    private static final BellBorcaSpectralSergeantAbility instance = new BellBorcaSpectralSergeantAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    private BellBorcaSpectralSergeantAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("note the mana value of each card as it's put into exile"));
    }

    @Override
    public BellBorcaSpectralSergeantAbility copy() {
        return instance;
    }

    public static BellBorcaSpectralSergeantAbility getInstance() {
        return instance;
    }
}

enum BellBorcaSpectralSergeantValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        BellBorcaSpectralSergeantWatcher watcher = game.getState().getWatcher(BellBorcaSpectralSergeantWatcher.class);
        return watcher == null ? 0 : watcher.getValue(sourceAbility.getSourceId());
    }

    @Override
    public BellBorcaSpectralSergeantValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class BellBorcaSpectralSergeantWatcher extends Watcher {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new AbilityPredicate(BellBorcaSpectralSergeantAbility.class));
    }

    private final Map<UUID, Integer> cmcMap = new HashMap<>();

    BellBorcaSpectralSergeantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null || card.isFaceDown(game)) {
            return;
        }
        int cmc = card.getManaValue();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            if (permanent == null
                    || cmcMap.get(permanent.getId()) != null
                    && cmcMap.get(permanent.getId()) >= cmc) {
                continue;
            }
            cmcMap.put(permanent.getId(), cmc);
        }
    }

    @Override
    public void reset() {
        cmcMap.clear();
        super.reset();
    }

    int getValue(UUID sourceId) {
        return cmcMap.getOrDefault(sourceId, 0);
    }
}
