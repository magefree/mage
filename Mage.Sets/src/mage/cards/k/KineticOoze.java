package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class KineticOoze extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment with mana value X or less");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public KineticOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When this creature enters, destroy up to one target artifact or enchantment with mana value X or less.
        // If X is 5 or more, you draw a card.
        // If X is 10 or more, double the number of +1/+1 counters on any number of other target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new DestroyTargetEffect()
                .setText("destroy up to one target artifact or enchantment with mana value X or less")
        );

        ability.addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(1),
            new KineticOozeXOrMoreCondition(5),
            "If X is 5 or more, you draw a card"
        ));

        ability.addEffect(new ConditionalOneShotEffect(
            new DoubleCountersTargetEffect(CounterType.P1P1),
            new KineticOozeXOrMoreCondition(10),
            "If X is 10 or more, double the number of +1/+1 counters on any number of other target creatures"
        ).setTargetPointer(new SecondTargetPointer()));

        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(KineticOozeTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private KineticOoze(final KineticOoze card) {
        super(card);
    }

    @Override
    public KineticOoze copy() {
        return new KineticOoze(this);
    }
}

class KineticOozeXOrMoreCondition implements Condition {
    private final int value;

    KineticOozeXOrMoreCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostsTag(game, source, "X", 0) >= value;
    }

    @Override
    public String toString() {
        return "X is " + value + " or more";
    }
}

enum KineticOozeTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int x = CardUtil.getSourceCostsTag(game, ability, "X", 0);
        ability.getTargets().clear();

        // Up to one artifact or enchantment with mana value X or less
        FilterPermanent filter2 = new FilterPermanent(
            "artifact or enchantment with mana value " + x + " or less");
        filter2.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.ENCHANTMENT.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.OR_LESS, x));
        ability.addTarget(new TargetPermanent(0, 1, filter2));

        // Any number of other target creatures
        if (x >= 10) {
            FilterCreaturePermanent filter3 = new FilterCreaturePermanent("other target creatures");
            filter3.add(AnotherPredicate.instance);
            ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter3));
        }
    }
}
