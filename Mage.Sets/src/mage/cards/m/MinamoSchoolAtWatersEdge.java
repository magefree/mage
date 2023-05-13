

package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class MinamoSchoolAtWatersEdge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("legendary permanent");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }


    public MinamoSchoolAtWatersEdge (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.supertype.add(SuperType.LEGENDARY);
        this.addAbility(new BlueManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(),  new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public MinamoSchoolAtWatersEdge (final MinamoSchoolAtWatersEdge card) {
        super(card);
    }

    @Override
    public MinamoSchoolAtWatersEdge copy() {
        return new MinamoSchoolAtWatersEdge(this);
    }

}
