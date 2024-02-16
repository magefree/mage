
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class RhysticDeluge extends CardImpl {

    public RhysticDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // {U}: Tap target creature unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new TapTargetEffect(), new ManaCostsImpl<>("{1}")), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RhysticDeluge(final RhysticDeluge card) {
        super(card);
    }

    @Override
    public RhysticDeluge copy() {
        return new RhysticDeluge(this);
    }
}
