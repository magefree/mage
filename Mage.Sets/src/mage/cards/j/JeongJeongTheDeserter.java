package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeongJeongTheDeserter extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Lesson spell");

    static {
        filter.add(SubType.LESSON.getPredicate());
    }

    public JeongJeongTheDeserter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Firebending 1
        this.addAbility(new FirebendingAbility(1));

        // Exhaust -- {3}: Put a +1/+1 counter on Jeong Jeong. When you next cast a Lesson spell this turn, copy it and you may choose new targets for the copy.
        Ability ability = new ExhaustAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(3));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility(filter)));
        this.addAbility(ability);
    }

    private JeongJeongTheDeserter(final JeongJeongTheDeserter card) {
        super(card);
    }

    @Override
    public JeongJeongTheDeserter copy() {
        return new JeongJeongTheDeserter(this);
    }
}
