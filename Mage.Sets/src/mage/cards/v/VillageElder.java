
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class VillageElder extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a Forest");
    static{
        filter.add(SubType.FOREST.getPredicate());
    }

    public VillageElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {tap}, Sacrifice a Forest: Regenerate target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VillageElder(final VillageElder card) {
        super(card);
    }

    @Override
    public VillageElder copy() {
        return new VillageElder(this);
    }
}
