package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesMonarchSourceControllerTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MonarchIsNotSetCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarscreamSeekerLeader extends CardImpl {

    public StarscreamSeekerLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Starscream deals combat damage to a player, if there is no monarch, that player becomes the monarch.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
            new DealsCombatDamageToAPlayerTriggeredAbility(new BecomesMonarchTargetEffect(), false, true),
            MonarchIsNotSetCondition.instance,
            "Whenever {this} deals combat damage to a player, if there is no monarch, that player becomes the monarch."
        ));

        // Whenever you become the monarch, convert Starscream.
        this.addAbility(new BecomesMonarchSourceControllerTriggeredAbility(
            new TransformSourceEffect().setText("convert {this}")
        ));
    }

    private StarscreamSeekerLeader(final StarscreamSeekerLeader card) {
        super(card);
    }

    @Override
    public StarscreamSeekerLeader copy() {
        return new StarscreamSeekerLeader(this);
    }
}
