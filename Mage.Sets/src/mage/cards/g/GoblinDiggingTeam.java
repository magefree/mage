
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Quercitron
 */
public final class GoblinDiggingTeam extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public GoblinDiggingTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice Goblin Digging Team: Destroy target Wall.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GoblinDiggingTeam(final GoblinDiggingTeam card) {
        super(card);
    }

    @Override
    public GoblinDiggingTeam copy() {
        return new GoblinDiggingTeam(this);
    }
}
