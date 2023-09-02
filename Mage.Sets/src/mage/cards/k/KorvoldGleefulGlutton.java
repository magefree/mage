package mage.cards.k;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Xanderhall
 */
public final class KorvoldGleefulGlutton extends CardImpl {

    public KorvoldGleefulGlutton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each card type among permanents you've sacrificed this turn.
        Ability staticAbility = new SimpleStaticAbility(Zone.ALL, 
            new SpellCostReductionSourceEffect(CardTypesAmongSacrificedPermanentsCount.instance)
                .setText("this spell costs {1} less to cast for each card type among permanents you've sacrificed this turn")
        );
        staticAbility.addHint(new ValueHint("Card types among permanents you've sacrificed this turn", CardTypesAmongSacrificedPermanentsCount.instance));
        
        this.addAbility(staticAbility, new KorvoldGleefulGluttonWatcher());

        // Flying, Trample, Haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Whenever Korvold deals combat damage to a player, put X +1/+1 counters on Korvold and draw X cards, where X is the number of permanent types among cards in your graveyard.
        Ability combatDamageAbility = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), PermanentTypesInGraveyardCount.instance, true)
                        .setText("put X +1/+1 counters on {this}"),
                false
        );
        combatDamageAbility.addEffect(new DrawCardSourceControllerEffect(PermanentTypesInGraveyardCount.instance).setText("and draw X cards, where X is the number of permanent types among cards in your graveyard"));
        combatDamageAbility.addHint(new ValueHint("Permanent types among cards in your graveyard", PermanentTypesInGraveyardCount.instance));
        this.addAbility(combatDamageAbility);
    }

    private KorvoldGleefulGlutton(final KorvoldGleefulGlutton card) {
        super(card);
    }

    @Override
    public KorvoldGleefulGlutton copy() {
        return new KorvoldGleefulGlutton(this);
    }
}

class KorvoldGleefulGluttonWatcher extends Watcher {

    private final Map<UUID, Set<CardType>> map = new HashMap<>();

    KorvoldGleefulGluttonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SACRIFICED_PERMANENT) {
            return;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());

        if (permanent != null) {
            permanent.getCardType(game).forEach(type -> 
                map.computeIfAbsent(event.getPlayerId(), (key) -> new HashSet<>()).add(type)
            );
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    public int getNumberOfTypes(UUID playerId) {
        return map.computeIfAbsent(playerId, (key) -> new HashSet<>()).size();
    };
}

enum CardTypesAmongSacrificedPermanentsCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        KorvoldGleefulGluttonWatcher watcher = game.getState().getWatcher(KorvoldGleefulGluttonWatcher.class);
        return watcher == null ? 0 : watcher.getNumberOfTypes(sourceAbility.getControllerId());
    }

    @Override
    public CardTypesAmongSacrificedPermanentsCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "card types among permanents you sacrificed";
    }
}

enum PermanentTypesInGraveyardCount implements DynamicValue {
    instance;

    @Override
    public PermanentTypesInGraveyardCount copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }

        return player.getGraveyard().getCards(game).stream()
            .map(card -> card.getCardType(game))
            .flatMap(types -> types.stream().filter(CardType::isPermanentType))
            .distinct()
            .mapToInt(x -> 1)
            .sum();
    }

    @Override
    public String getMessage() {
        return "permanent types among cards in your graveyard";
    }

}