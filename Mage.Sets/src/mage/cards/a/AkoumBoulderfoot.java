
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class AkoumBoulderfoot extends CardImpl {

    public AkoumBoulderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        Target target = new TargetAnyTarget();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private AkoumBoulderfoot(final AkoumBoulderfoot card) {
        super(card);
    }

    @Override
    public AkoumBoulderfoot copy() {
        return new AkoumBoulderfoot(this);
    }
}
