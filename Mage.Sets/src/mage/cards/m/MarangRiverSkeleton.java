
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MarangRiverSkeleton extends CardImpl {

    public MarangRiverSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Regenerate Marang River Skeleton.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));

        // Megamorph {3}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{B}"), true));
    }

    private MarangRiverSkeleton(final MarangRiverSkeleton card) {
        super(card);
    }

    @Override
    public MarangRiverSkeleton copy() {
        return new MarangRiverSkeleton(this);
    }
}
