
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class MudbuttonTorchrunner extends CardImpl {

    public MudbuttonTorchrunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        // When Mudbutton Torchrunner dies, it deals 3 damage to any target.
        Ability ability = new DiesTriggeredAbility(new DamageTargetEffect(3, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public MudbuttonTorchrunner(final MudbuttonTorchrunner card) {
        super(card);
    }

    @Override
    public MudbuttonTorchrunner copy() {
        return new MudbuttonTorchrunner(this);
    }
}
