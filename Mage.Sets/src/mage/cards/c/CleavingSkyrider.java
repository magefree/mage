package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class CleavingSkyrider extends CardImpl {

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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new AttackingCreatureCount())),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, it deals X damage to any target, where X is the number of attacking creatures."
        );
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
