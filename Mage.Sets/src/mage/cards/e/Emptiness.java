package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TwoOfManaColorSpentCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Emptiness extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public Emptiness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W/B}{W/B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When this creature enters, if {W}{W} was spent to cast it, return target creature card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .withInterveningIf(TwoOfManaColorSpentCondition.WHITE);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // When this creature enters, if {B}{B} was spent to cast it, put three -1/-1 counters on up to one target creature.
        ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)))
                .withInterveningIf(TwoOfManaColorSpentCondition.BLACK);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Evoke {W/B}{W/B}
        this.addAbility(new EvokeAbility("{W/B}{W/B}"));
    }

    private Emptiness(final Emptiness card) {
        super(card);
    }

    @Override
    public Emptiness copy() {
        return new Emptiness(this);
    }
}
