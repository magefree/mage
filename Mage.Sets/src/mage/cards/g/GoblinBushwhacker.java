
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class GoblinBushwhacker extends CardImpl {

    public GoblinBushwhacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {R} (You may pay an additional {R} as you cast this spell.)
        this.addAbility(new KickerAbility("{R}"));

        // When Goblin Bushwhacker enters the battlefield, if it was kicked, creatures you control get +1/+0 and gain haste until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn), false);
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), 
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                KickedCondition.ONCE,
                "When {this} enters the battlefield, "
                + "if it was kicked, "
                + "creatures you control get +1/+0 and gain haste until end of turn."
        ));
    }

    private GoblinBushwhacker(final GoblinBushwhacker card) {
        super(card);
    }

    @Override
    public GoblinBushwhacker copy() {
        return new GoblinBushwhacker(this);
    }
}
