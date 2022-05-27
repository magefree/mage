package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class VoltaicVisionary extends CardImpl {

    public VoltaicVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.v.VoltChargedBerserker.class;

        // {T}: Voltaic Visionary deals 2 damage to you. Exile the top card of your library. You may play that card this turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DamageControllerEffect(2), new TapSourceCost()
        );
        ability.addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(1));
        this.addAbility(ability);

        // When you play a card exiled with Voltaic Visionary, transform Voltaic Visionary.
        this.addAbility(new TransformAbility());
        this.addAbility(new VoltaicVisionaryTriggeredAbility());
    }

    private VoltaicVisionary(final VoltaicVisionary card) {
        super(card);
    }

    @Override
    public VoltaicVisionary copy() {
        return new VoltaicVisionary(this);
    }
}

class VoltaicVisionaryTriggeredAbility extends TriggeredAbilityImpl {

    VoltaicVisionaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
        this.addWatcher(new VoltaicVisionaryWatcher());
    }

    private VoltaicVisionaryTriggeredAbility(final VoltaicVisionaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VoltaicVisionaryTriggeredAbility copy() {
        return new VoltaicVisionaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        if (card == null) {
            return false;
        }
        return VoltaicVisionaryWatcher.checkCard(card, this, game);
    }

    @Override
    public String getTriggerPhrase() {
        return "When you play a card exiled with {this}, ";
    }
}

class VoltaicVisionaryWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = Collections.unmodifiableSet(new HashSet<>());

    VoltaicVisionaryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || ((ZoneChangeEvent) event).getToZone() != Zone.EXILED
                || ((ZoneChangeEvent) event).getFromZone() != Zone.LIBRARY) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return;
        }
        UUID exileId = game
                .getExile()
                .getExileZones()
                .stream().filter(exileZone -> exileZone.contains(card.getId()))
                .map(ExileZone::getId)
                .findFirst()
                .orElse(null);
        if (exileId == null) {
            return;
        }
        map.computeIfAbsent(exileId, x -> new HashSet<>()).add(new MageObjectReference(card, game));
    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    static boolean checkCard(Card card, Ability source, Game game) {
        return card != null
                && game.getState()
                        .getWatcher(VoltaicVisionaryWatcher.class).map
                        .getOrDefault(CardUtil.getCardExileZoneId(game, source), emptySet)
                        .contains(new MageObjectReference(card, game, -1));
    }
}
