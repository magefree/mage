
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class ElvishHunter extends CardImpl {

    public ElvishHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}: Target creature doesn't untap during its controller's next untap step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepTargetEffect("Target creature"),
            new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ElvishHunter(final ElvishHunter card) {
        super(card);
    }

    @Override
    public ElvishHunter copy() {
        return new ElvishHunter(this);
    }
}
