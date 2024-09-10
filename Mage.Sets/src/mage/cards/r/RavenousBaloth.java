
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class RavenousBaloth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.BEAST, "a Beast");

    public RavenousBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Sacrifice a Beast: You gain 4 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4),
                new SacrificeTargetCost(filter)));
    }

    private RavenousBaloth(final RavenousBaloth card) {
        super(card);
    }

    @Override
    public RavenousBaloth copy() {
        return new RavenousBaloth(this);
    }
}
