package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueConditionHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
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
public final class SerahFarron extends TransformingDoubleFacedCard {

    private static final FilterCard filter
            = new FilterCreatureCard("the first legendary creature spell you cast each turn");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("you control two or more other legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SerahFarronPredicate.instance);
        filter2.add(AnotherPredicate.instance);
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2);
    private static final Hint hint = new ValueConditionHint(
            "You control two or more other legendary creatures",
            new PermanentsOnBattlefieldCount(filter2), condition
    );

    public SerahFarron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CITIZEN}, "{1}{G}{W}",
                "Crystallized Serah",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "WG");

        // Serah Farron
        this.getLeftHalfCard().setPT(2, 2);

        // The first legendary creature spell you cast each turn costs {2} less to cast.
        Ability ability = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2));
        ability.addWatcher(new SerahFarronWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of combat on your turn, if you control two or more other legendary creatures, you may transform Serah Farron.
        this.getLeftHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(
                new TransformSourceEffect(), true
        ).withInterveningIf(condition).addHint(hint));

        // Crystallized Serah
        // The first legendary creature spell you cast each turn costs {2} less to cast.
        this.getRightHalfCard().addAbility(ability.copy());

        // Legendary creatures you control get +2/+2.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_LEGENDARY
        )));
    }

    private SerahFarron(final SerahFarron card) {
        super(card);
    }

    @Override
    public SerahFarron copy() {
        return new SerahFarron(this);
    }
}

enum SerahFarronPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return !SerahFarronWatcher.checkPlayer(input.getPlayerId(), game);
    }

    @Override
    public String toString() {
        return "The first creature spell you cast each turn";
    }
}

class SerahFarronWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    public SerahFarronWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.isCreature(game) && spell.isLegendary(game)) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(SerahFarronWatcher.class).set.contains(playerId);
    }
}
