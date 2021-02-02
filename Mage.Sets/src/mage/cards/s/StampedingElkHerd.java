
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StampedingElkHerd extends CardImpl {

    public StampedingElkHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // <i>Formidable</i> &mdash; Whenever Stampeding Elk Herd attacks, if creatures you control have total power 8 or greater, creatures you control gain trample until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent()), false),
                FormidableCondition.instance,
                "<i>Formidable</i> &mdash; Whenever {this} attacks, if creatures you control have total power 8 or greater, creatures you control gain trample until end of turn."
        ));
    }

    private StampedingElkHerd(final StampedingElkHerd card) {
        super(card);
    }

    @Override
    public StampedingElkHerd copy() {
        return new StampedingElkHerd(this);
    }
}
