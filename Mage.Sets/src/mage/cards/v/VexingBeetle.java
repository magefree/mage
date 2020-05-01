package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VexingBeetle extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, ComparisonType.EQUAL_TO, 0, false
    );

    public VexingBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vexing Beetle can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()));

        // Vexing Beetle gets +3/+3 as long as no opponent controls a creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                condition, "{this} gets +3/+3 as long as no opponent controls a creature"
        )));
    }

    private VexingBeetle(final VexingBeetle card) {
        super(card);
    }

    @Override
    public VexingBeetle copy() {
        return new VexingBeetle(this);
    }
}
