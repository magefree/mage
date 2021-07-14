
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
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
public final class PerilousMyr extends CardImpl {

    public PerilousMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Perilous Myr dies, it deals 2 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PerilousMyr(final PerilousMyr card) {
        super(card);
    }

    @Override
    public PerilousMyr copy() {
        return new PerilousMyr(this);
    }

}
