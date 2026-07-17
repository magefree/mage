package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BecomesMonarchSourceControllerTriggeredAbility;
import mage.abilities.common.CombatDamageDealtToYouTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.condition.common.MonarchIsNotSetCondition;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarscreamPowerHungry extends TransformingDoubleFacedCard {

    public StarscreamPowerHungry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{3}{B}",
                "Starscream, Seeker Leader",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "B"
        );

        // Starscream, Power Hungry
        this.getLeftHalfCard().setPT(2, 3);

        // More Than Meets the Eye {2}{B}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{B}"));

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, if you're the monarch, target opponent loses 2 life.
        Ability ability = new DrawCardControllerTriggeredAbility(new LoseLifeTargetEffect(2), false)
                .withInterveningIf(MonarchIsSourceControllerCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability.addHint(MonarchHint.instance));

        // Whenever one or more creatures deal combat damage to you, convert Starscream.
        this.getLeftHalfCard().addAbility(new CombatDamageDealtToYouTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}")
        ));

        // Starscream, Seeker Leader
        this.getRightHalfCard().setPT(2, 3);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Starscream deals combat damage to a player, if there is no monarch, that player becomes the monarch.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new BecomesMonarchTargetEffect().setText("that player becomes the monarch"), false, true
        ).withInterveningIf(MonarchIsNotSetCondition.instance).addHint(MonarchHint.instance));

        // Whenever you become the monarch, convert Starscream.
        this.getRightHalfCard().addAbility(new BecomesMonarchSourceControllerTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}")
        ));
    }

    private StarscreamPowerHungry(final StarscreamPowerHungry card) {
        super(card);
    }

    @Override
    public StarscreamPowerHungry copy() {
        return new StarscreamPowerHungry(this);
    }
}
