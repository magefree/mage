package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
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

        // When Archangel of Wrath enters the battlefield, if it was kicked, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(2, "it")
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // When Archangel of Wrath enters the battlefield, if it was kicked twice, it deals 2 damage to any target.
        ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(2, "it")
        ).withInterveningIf(KickedCondition.TWICE);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ArchangelOfWrath(final ArchangelOfWrath card) {
        super(card);
    }

    @Override
    public ArchangelOfWrath copy() {
        return new ArchangelOfWrath(this);
    }
}
