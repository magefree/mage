package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.CombatDamageDealtToYouTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarscreamPowerHungry extends CardImpl {

    public StarscreamPowerHungry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.s.StarscreamSeekerLeader.class;

        // More Than Meets the Eye {2}{B}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, if you're the monarch, target opponent loses 2 life.
        TriggeredAbility trigger = new ConditionalInterveningIfTriggeredAbility(
            new DrawCardControllerTriggeredAbility(new LoseLifeTargetEffect(2), false),
            MonarchIsSourceControllerCondition.instance,
            "Whenever you draw a card, if you're the monarch, target opponent loses 2 life."
        );
        trigger.addTarget(new TargetOpponent());
        this.addAbility(trigger);

        // Whenever one or more creatures deal combat damage to you, convert Starscream.
        this.addAbility(new CombatDamageDealtToYouTriggeredAbility(
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