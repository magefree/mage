package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdbertWarriorOfDarkness extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");
    private static final FilterSpell filter2 = new FilterSpell("a black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ArdbertWarriorOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a white spell, put a +1/+1 counter on each legendary creature you control. They gain vigilance until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        ), filter, false);
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        ).setText("They gain vigilance until end of turn"));
        this.addAbility(ability);

        // Whenever you cast a black spell, put a +1/+1 counter on each legendary creature you control. They gain menace until end of turn.
        ability = new SpellCastControllerTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        ), filter2, false);
        ability.addEffect(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        ).setText("They gain menace until end of turn"));
        this.addAbility(ability);
    }

    private ArdbertWarriorOfDarkness(final ArdbertWarriorOfDarkness card) {
        super(card);
    }

    @Override
    public ArdbertWarriorOfDarkness copy() {
        return new ArdbertWarriorOfDarkness(this);
    }
}
