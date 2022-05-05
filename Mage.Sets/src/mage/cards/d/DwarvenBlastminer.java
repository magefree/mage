
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class DwarvenBlastminer extends CardImpl {
    public DwarvenBlastminer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}, {tap}: Destroy target nonbasic land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
        
        // Morph {R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{R}")));
    }

    private DwarvenBlastminer(final DwarvenBlastminer card) {
        super(card);
    }

    @Override
    public DwarvenBlastminer copy() {
        return new DwarvenBlastminer(this);
    }
}
