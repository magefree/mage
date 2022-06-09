package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparktongueDragon extends CardImpl {

    public SparktongueDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sparktongue Dragon enters the battlefield, you may pay {2}{R}. When you do, it deals 3 damage to any target.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(3), false,
                "it deals 3 damage to any target"
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{2}{R}"),
                "Pay {2}{R} to deal 3 damage?"
        )));
    }

    private SparktongueDragon(final SparktongueDragon card) {
        super(card);
    }

    @Override
    public SparktongueDragon copy() {
        return new SparktongueDragon(this);
    }
}
