package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VineGecko extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(VineGeckoPredicate.instance);
    }

    public VineGecko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // The first kicked spell you cast each turn costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("the first kicked spell you cast each turn costs {1} less to cast")), new VineGeckoWatcher());

        // Whenever you cast a kicked spell, put a +1/+1 counter on Vine Gecko.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_KICKED_A, false
        ));
    }

    private VineGecko(final VineGecko card) {
        super(card);
    }

    @Override
    public VineGecko copy() {
        return new VineGecko(this);
    }
}

enum VineGeckoPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Card>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        VineGeckoWatcher watcher = game.getState().getWatcher(VineGeckoWatcher.class);
        if (watcher == null || watcher.checkPlayer(input.getPlayerId())) {
            return false;
        }

        for (Ability ability : input.getObject().getAbilities(game)) {
            if (ability instanceof KickerAbility
                    && ((KickerAbility) ability).getKickedCounter(game, input.getObject().getSpellAbility()) > 0) {
                return true;
            }
        }
        return false;
    }
}

class VineGeckoWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    VineGeckoWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && StaticFilters.FILTER_SPELL_KICKED_A.match(
                spell, getSourceId(), getControllerId(), game
        )) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public boolean checkPlayer(UUID playerId) {
        return playerSet.contains(playerId);
    }
}
