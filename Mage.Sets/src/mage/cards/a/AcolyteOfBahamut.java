package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
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
public final class AcolyteOfBahamut extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(AcolyteOfBahamutPredicate.instance);
    }

    public AcolyteOfBahamut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "The first Dragon spell you cast each turn costs {2} less to cast."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new SimpleStaticAbility(
                        new SpellsCostReductionControllerEffect(filter, 2)
                                .setText("the first Dragon spell you cast each turn costs {2} less to cast")
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).withForceQuotes()), new AcolyteOfBahamutWatcher());
    }

    private AcolyteOfBahamut(final AcolyteOfBahamut card) {
        super(card);
    }

    @Override
    public AcolyteOfBahamut copy() {
        return new AcolyteOfBahamut(this);
    }
}

enum AcolyteOfBahamutPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().hasSubtype(SubType.DRAGON, game)
                && !AcolyteOfBahamutWatcher.checkPlayer(input.getPlayerId(), game);
    }
}

class AcolyteOfBahamutWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    AcolyteOfBahamutWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.hasSubtype(SubType.DRAGON, game)) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(AcolyteOfBahamutWatcher.class)
                .playerSet
                .contains(playerId);
    }
}
