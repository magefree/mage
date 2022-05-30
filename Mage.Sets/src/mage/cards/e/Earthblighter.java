
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class Earthblighter extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Goblin");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public Earthblighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {tap}, Sacrifice a Goblin: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private Earthblighter(final Earthblighter card) {
        super(card);
    }

    @Override
    public Earthblighter copy() {
        return new Earthblighter(this);
    }
}
