package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author DominionSpy
 */
public final class MelekReforgedResearcher extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("the first instant or sorcery spell");

    static {
        filter.add(MelekReforgedResearcherPredicate.instance);
    }

    public MelekReforgedResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEIRD);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Melek, Reforged Researcher's power and toughness are each equal to twice the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(
                        new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY, 2))
                .setText("{this}'s power and toughness are each equal to twice the number of instant and sorcery cards in your graveyard")));

        // The first instant or sorcery spell you cast each turn costs {3} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filter, 3);
        effect.setText("The first instant or sorcery spell you cast each turn costs {3} less to cast");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect),
                new MelekReforgedResearcherWatcher());
    }

    private MelekReforgedResearcher(final MelekReforgedResearcher card) {
        super(card);
    }

    @Override
    public MelekReforgedResearcher copy() {
        return new MelekReforgedResearcher(this);
    }
}

enum MelekReforgedResearcherPredicate implements ObjectSourcePlayerPredicate<Controllable> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        if (input.getObject() instanceof Card &&
                ((Card) input.getObject()).isInstantOrSorcery(game)) {
            MelekReforgedResearcherWatcher watcher = game.getState().getWatcher(MelekReforgedResearcherWatcher.class);
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

class MelekReforgedResearcherWatcher extends Watcher {

    private final Map<UUID, Integer> playerInstantOrSorcerySpells = new HashMap<>();

    MelekReforgedResearcherWatcher() {
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
