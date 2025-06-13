package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CleavingSkyrider extends CardImpl {

    private static final DynamicValue xValue = new AttackingCreatureCount("the number of attacking creatures");

    public CleavingSkyrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Kicker {2}{R}
        this.addAbility(new KickerAbility("{2}{R}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Cleaving Skyrider enters the battlefield, if it was kicked, it deals X damage to any target, where X is the number of attacking creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(xValue, "it").setText("it deals X damage to any target, where X is the number of attacking creatures")
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CleavingSkyrider(final CleavingSkyrider card) {
        super(card);
    }

    @Override
    public CleavingSkyrider copy() {
        return new CleavingSkyrider(this);
    }
}
