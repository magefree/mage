
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class ConsulsLieutenant extends CardImpl {

    public ConsulsLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Renown 1
        this.addAbility(new RenownAbility(1));
        // Whenever Consul's Lieutenant attacks, if it's renowned, other attacking creatures you control get +1/+1 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new AttacksTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, new FilterAttackingCreature("other attacking creatures you control"), true), false),
                RenownedSourceCondition.instance,
                "Whenever Consul's Lieutenant attacks, if it's renowned, other attacking creatures you control get +1/+1 until end of turn."));

    }

    private ConsulsLieutenant(final ConsulsLieutenant card) {
        super(card);
    }

    @Override
    public ConsulsLieutenant copy() {
        return new ConsulsLieutenant(this);
    }
}
