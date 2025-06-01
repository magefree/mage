package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueConditionHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
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
public final class SerahFarron extends CardImpl {

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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.c.CrystallizedSerah.class;

        // The first legendary creature spell you cast each turn costs {2} less to cast.
        this.addAbility(makeAbility(), new SerahFarronWatcher());

        // At the beginning of combat on your turn, if you control two or more other legendary creatures, you may transform Serah Farron.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new TransformSourceEffect(), true
        ).withInterveningIf(condition).addHint(hint));
    }

    private SerahFarron(final SerahFarron card) {
        super(card);
    }

    @Override
    public SerahFarron copy() {
        return new SerahFarron(this);
    }

    public static Ability makeAbility() {
        return new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2));
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
