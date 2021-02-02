
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
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
 * @author Loki
 */
public final class NezumiShadowWatcher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Ninja");

    static {
        filter.add(SubType.NINJA.getPredicate());
    }

    public NezumiShadowWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        // Sacrifice Nezumi Shadow-Watcher: Destroy target Ninja.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NezumiShadowWatcher(final NezumiShadowWatcher card) {
        super(card);
    }

    @Override
    public NezumiShadowWatcher copy() {
        return new NezumiShadowWatcher(this);
    }
}
