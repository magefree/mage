package mage.cards.e;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jimga150
 */
public final class ElugeTheShorelessSea extends CardImpl {

    private static final FilterControlledPermanent islandFilter = new FilterControlledPermanent("Islands you control");
    private static final FilterCard spellFilter = new FilterInstantOrSorceryCard("the first instant or sorcery spell you cast each turn");
    private static final FilterControlledLandPermanent floodLandFilter = new FilterControlledLandPermanent("land you control with a flood counter on it.");

    static {
        islandFilter.add(SubType.ISLAND.getPredicate());
    }

    static {
        spellFilter.add(ElugeTheShorelessSeaPredicate.instance);
    }
    
    static {
        floodLandFilter.add(CounterType.FLOOD.getPredicate());
    }

    public ElugeTheShorelessSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Eluge's power and toughness are each equal to the number of Islands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(islandFilter))));

        // Whenever Eluge enters or attacks, put a flood counter on target land. It's an Island in addition to its other
        // types for as long as it has a flood counter on it.
        // Based on Xolatoyac, the Smiling Flood
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.FLOOD.createInstance()));
        ability.addEffect(new ElugeTheShorelessSeaEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // The first instant or sorcery spell you cast each turn costs {U} (or {1}) less to cast for each land you
        // control with a flood counter on it.
        Effect effect = new SpellsCostReductionControllerEffect(spellFilter, new ManaCostsImpl<>("{U}"), new PermanentsOnBattlefieldCount(floodLandFilter), true);
        this.addAbility(new SimpleStaticAbility(effect),
                new ElugeTheShorelessSeaWatcher());
    }

    private ElugeTheShorelessSea(final ElugeTheShorelessSea card) {
        super(card);
    }

    @Override
    public ElugeTheShorelessSea copy() {
        return new ElugeTheShorelessSea(this);
    }
}

enum ElugeTheShorelessSeaPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null &&
                input.getObject().isInstantOrSorcery(game)) {
            ElugeTheShorelessSeaWatcher watcher = game.getState().getWatcher(ElugeTheShorelessSeaWatcher.class);
            return watcher != null &&
                    watcher.getInstantOrSorcerySpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first instant or sorcery spell you cast each turn";
    }
}

// Based on XolatoyacTheSmilingFloodEffect
class ElugeTheShorelessSeaEffect extends BecomesBasicLandTargetEffect {

    ElugeTheShorelessSeaEffect() {
        super(Duration.Custom, false, false, SubType.ISLAND);
        staticText = "It's an Island in addition to its other types for as long as it has a flood counter on it";
    }

    private ElugeTheShorelessSeaEffect(final ElugeTheShorelessSeaEffect effect) {
        super(effect);
    }

    @Override
    public ElugeTheShorelessSeaEffect copy() {
        return new ElugeTheShorelessSeaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (land == null || land.getCounters(game).getCount(CounterType.FLOOD) < 1) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}

// Based on MelekReforgedResearcherWatcher
class ElugeTheShorelessSeaWatcher extends Watcher {

    private final Map<UUID, Integer> playerInstantOrSorcerySpells = new HashMap<>();

    ElugeTheShorelessSeaWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch (GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }

        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return;
        }
        playerInstantOrSorcerySpells.put(event.getPlayerId(),
                getInstantOrSorcerySpellsCastThisTurn(event.getPlayerId()) + 1);
    }

    @Override
    public void reset() {
        playerInstantOrSorcerySpells.clear();
        super.reset();
    }

    public int getInstantOrSorcerySpellsCastThisTurn(UUID playerId) {
        return playerInstantOrSorcerySpells.getOrDefault(playerId, 0);
    }
}
