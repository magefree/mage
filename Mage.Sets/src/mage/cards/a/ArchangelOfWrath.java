package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchangelOfWrath extends CardImpl {

    public ArchangelOfWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Kicker {B} and/or {R}
        KickerAbility kickerAbility = new KickerAbility("{B}");
        kickerAbility.addKickerCost("{R}");
        this.addAbility(kickerAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        triggeredAbility.addTarget(new TargetAnyTarget());
        // When Archangel of Wrath enters the battlefield, if it was kicked, it deals 2 damage to any target.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility, KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, it deals 2 damage to any target."
        ));

        // When Archangel of Wrath enters the battlefield, if it was kicked twice, it deals 2 damage to any target.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility.copy(), KickedCondition.TWICE, "When {this} enters the battlefield, " +
                "if it was kicked twice, it deals 2 damage to any target."
        ));
    }

    private ArchangelOfWrath(final ArchangelOfWrath card) {
        super(card);
    }

    @Override
    public ArchangelOfWrath copy() {
        return new ArchangelOfWrath(this);
    }
}
