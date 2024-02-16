
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class KarplusanWolverine extends CardImpl {

    public KarplusanWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.WOLVERINE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Karplusan Wolverine becomes blocked, you may have it deal 1 damage to any target.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new DamageTargetEffect(1), true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private KarplusanWolverine(final KarplusanWolverine card) {
        super(card);
    }

    @Override
    public KarplusanWolverine copy() {
        return new KarplusanWolverine(this);
    }
}
