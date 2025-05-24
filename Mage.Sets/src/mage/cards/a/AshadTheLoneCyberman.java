package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.constants.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author padfoothelix
 */
public final class AshadTheLoneCyberman extends CardImpl {

    private static final String rule = "The first nonlegendary artifact spell you cast each turn has casualty 2. " +
       "<i>(As you cast it, you may sacrifice a creature with power 2 or greater. When you do, copy it. " +
       "A copy of an artifact spell becomes a token.)</i>";
    private static final FilterNonlandCard filter = new FilterNonlandCard("the first nonlegendary artifact you cast each turn");
    static {
        filter.add(AshadTheLoneCybermanSpellPredicate.instance);
    }

    public AshadTheLoneCyberman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CYBERMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // The first nonlegendary artifact spell you cast each turn has casualty 2.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(
                        new CasualtyAbility(2), 
                        filter
                ).setText(rule)),
                new AshadTheLoneCybermanWatcher()
        );

        // Whenever you sacrifice another creature, put a +1/+1 counter on Ashad, the Lone Cyberman.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
    }

    private AshadTheLoneCyberman(final AshadTheLoneCyberman card) {
        super(card);
    }

    @Override
    public AshadTheLoneCyberman copy() {
        return new AshadTheLoneCyberman(this);
    }
}

class AshadTheLoneCybermanWatcher extends Watcher {

    // Based on Peri Brown which is based on Conduit of Ruin

    private final Map<UUID, Integer> nonlegendaryArtifactSpells; // player id -> number

    public AshadTheLoneCybermanWatcher() {
        super(WatcherScope.GAME);
        nonlegendaryArtifactSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null 
                    && !spell.isLegendary(game)
                    && spell.isArtifact(game)) {
                nonlegendaryArtifactSpells.put(event.getPlayerId(), nonlegendaryArtifactSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    public int nonlegendaryArtifactSpellsCastThisTurn(UUID playerId) {
        return nonlegendaryArtifactSpells.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        nonlegendaryArtifactSpells.clear();
    }
}


enum AshadTheLoneCybermanSpellPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null
                && !input.getObject().isLegendary(game)
                && input.getObject().isArtifact(game)) {
            AshadTheLoneCybermanWatcher watcher = game.getState().getWatcher(AshadTheLoneCybermanWatcher.class);
            return watcher != null && watcher.nonlegendaryArtifactSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first nonlegendary artifact spell you cast each turn";
    }
}
