
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class PsionicEntity extends CardImpl {

    public PsionicEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Psionic Entity deals 2 damage to any target and 3 damage to itself.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability.addEffect(new DamageSelfEffect(3).setText("and 3 damage to itself"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PsionicEntity(final PsionicEntity card) {
        super(card);
    }

    @Override
    public PsionicEntity copy() {
        return new PsionicEntity(this);
    }
}
