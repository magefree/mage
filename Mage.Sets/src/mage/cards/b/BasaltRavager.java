package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasaltRavager extends CardImpl {

    public BasaltRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Basalt Ravager enters the battlefield, it deals X damage to any target, where X is the greatest number of creatures you control that have a creature type in common.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(
                GreatestSharedCreatureTypeCount.instance
        ).setText("it deals X damage to any target, where X is the greatest number of " +
                "creatures you control that have a creature type in common"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BasaltRavager(final BasaltRavager card) {
        super(card);
    }

    @Override
    public BasaltRavager copy() {
        return new BasaltRavager(this);
    }
}
