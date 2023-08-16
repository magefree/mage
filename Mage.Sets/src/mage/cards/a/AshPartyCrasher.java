package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AshPartyCrasher extends CardImpl {

    public AshPartyCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Celebration -- Whenever Ash, Party Crasher attacks, if two or more nonland permanents entered the battlefield under your control this turn, put a +1/+1 counter on Ash.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false),
                CelebrationCondition.instance, "Whenever {this} attacks, if two or more nonland permanents " +
                "entered the battlefield under your control this turn, put a +1/+1 counter on {this}."
        );
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new CelebrationWatcher());
    }

    private AshPartyCrasher(final AshPartyCrasher card) {
        super(card);
    }

    @Override
    public AshPartyCrasher copy() {
        return new AshPartyCrasher(this);
    }
}
