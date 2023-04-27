
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class TrenchWurm extends CardImpl {

    public TrenchWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{R}, {tap}: Destroy target nonbasic land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetNonBasicLandPermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TrenchWurm(final TrenchWurm card) {
        super(card);
    }

    @Override
    public TrenchWurm copy() {
        return new TrenchWurm(this);
    }
}
