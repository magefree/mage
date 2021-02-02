
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Styxo
 */
public final class BountyCollector extends CardImpl {

    public BountyCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.QUARREN, SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Untap target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
        
        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, untap Bounty Collector.
        this.addAbility(new BountyAbility(new UntapSourceEffect()));
    }

    private BountyCollector(final BountyCollector card) {
        super(card);
    }

    @Override
    public BountyCollector copy() {
        return new BountyCollector(this);
    }
}
