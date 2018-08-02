
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class JorKadeenThePrevailer extends CardImpl {

    private static final String effectText = "<i>Metalcraft</i> &mdash; Creatures you control get +3/+0 as long as you control three or more artifacts.";

    public JorKadeenThePrevailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // <i>Metalcraft</i> &mdash; Creatures you control get +3/+0 as long as you control three or more artifacts.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostControlledEffect(3, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false),
                MetalcraftCondition.instance, effectText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public JorKadeenThePrevailer(final JorKadeenThePrevailer card) {
        super(card);
    }

    @Override
    public JorKadeenThePrevailer copy() {
        return new JorKadeenThePrevailer(this);
    }
}
