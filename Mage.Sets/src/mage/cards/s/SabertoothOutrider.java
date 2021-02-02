
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class SabertoothOutrider extends CardImpl {

    public SabertoothOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // <i>Formidable</i> &mdash; Whenever Sabertooth Outrider attacks, if creatures you control have total power 8 or greater, Sabertooth Outrider gains first strike until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), false),
                FormidableCondition.instance,
                "<i>Formidable</i> &mdash; Whenever {this} attacks, if creatures you control have total power 8 or greater, {this} gains first strike until end of turn."
        ));

    }

    private SabertoothOutrider(final SabertoothOutrider card) {
        super(card);
    }

    @Override
    public SabertoothOutrider copy() {
        return new SabertoothOutrider(this);
    }
}
