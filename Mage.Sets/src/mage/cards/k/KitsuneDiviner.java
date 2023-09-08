

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class KitsuneDiviner extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Spirit");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public KitsuneDiviner (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        
        // {T}: Tap target Spirit.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KitsuneDiviner(final KitsuneDiviner card) {
        super(card);
    }

    @Override
    public KitsuneDiviner copy() {
        return new KitsuneDiviner(this);
    }

}
