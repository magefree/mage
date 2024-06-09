
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author hanasu
 */
public final class RevekaWizardSavant extends CardImpl {

    public RevekaWizardSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}: Reveka, Wizard Savant deals 2 damage to any target and doesn't untap during your next untap step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
    }

    private RevekaWizardSavant(final RevekaWizardSavant card) {
        super(card);
    }

    @Override
    public RevekaWizardSavant copy() {
        return new RevekaWizardSavant(this);
    }
}
