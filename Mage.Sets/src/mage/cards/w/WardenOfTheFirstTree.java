
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class WardenOfTheFirstTree extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent FILTER2 = new FilterCreaturePermanent();

    static {
        FILTER.add(new SubtypePredicate(SubType.WARRIOR));
        FILTER2.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public WardenOfTheFirstTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new WardenOfTheFirstTree1(), "", Duration.Custom),
                new ManaCostsImpl("{1}{W/B}")));

        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BecomesCreatureSourceEffect(new WardenOfTheFirstTree2(), "", Duration.Custom),
                        new LockedInCondition(new SourceMatchesFilterCondition(FILTER)),
                        "If {this} is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink"),
                new ManaCostsImpl("{2}{W/B}{W/B}")
        ));

        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                        new SourceMatchesFilterCondition(FILTER2),
                        "If {this} is a Spirit, put five +1/+1 counters on it"),
                new ManaCostsImpl("{3}{W/B}{W/B}{W/B}")
        ));
    }

    public WardenOfTheFirstTree(final WardenOfTheFirstTree card) {
        super(card);
    }

    @Override
    public WardenOfTheFirstTree copy() {
        return new WardenOfTheFirstTree(this);
    }
}

class WardenOfTheFirstTree1 extends TokenImpl {

    public WardenOfTheFirstTree1() {
        super("Warden of the First Tree", "Human Warrior with base power and toughness 3/3");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    public WardenOfTheFirstTree1(final WardenOfTheFirstTree1 token) {
        super(token);
    }

    public WardenOfTheFirstTree1 copy() {
        return new WardenOfTheFirstTree1(this);
    }
}

class WardenOfTheFirstTree2 extends TokenImpl {

    public WardenOfTheFirstTree2() {
        super("Warden of the First Tree", "Human Spirit Warrior with trample and lifelink");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }
    public WardenOfTheFirstTree2(final WardenOfTheFirstTree2 token) {
        super(token);
    }

    public WardenOfTheFirstTree2 copy() {
        return new WardenOfTheFirstTree2(this);
    }
}
