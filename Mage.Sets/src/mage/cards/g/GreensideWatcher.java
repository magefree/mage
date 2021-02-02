
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class GreensideWatcher extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Gate");
    static {
        filter.add(SubType.GATE.getPredicate());
    }
    
    public GreensideWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {tap}: Untap target Gate.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GreensideWatcher(final GreensideWatcher card) {
        super(card);
    }

    @Override
    public GreensideWatcher copy() {
        return new GreensideWatcher(this);
    }
}
