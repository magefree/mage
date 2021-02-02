
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Backfir3
 */
public final class BarlsCage extends CardImpl {

    public BarlsCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

		// {3}: Target creature doesn't untap during its controller's next untap step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepTargetEffect("Target creature"), new GenericManaCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BarlsCage(final BarlsCage card) {
        super(card);
    }

    @Override
    public BarlsCage copy() {
        return new BarlsCage(this);
    }
}
