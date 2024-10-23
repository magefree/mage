
package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Leonan
 */
public final class DannyPink extends CardImpl {

    public DannyPink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Mentor
        this.addAbility(new MentorAbility());

        // Creatures you control have “Whenever one or more counters are put on this creature for the first time each turn, draw a card.”
        TriggeredAbility gainedAbility = new OneOrMoreCountersAddedTriggeredAbility(
            new DrawCardSourceControllerEffect(1), false
            ).setTriggerPhrase("Whenever one or more counters are put on this creature for the first time each turn, ");
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                gainedAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));

    }

    private DannyPink(final DannyPink card) {
        super(card);
    }

    @Override
    public DannyPink copy() {
        return new DannyPink(this);
    }

}