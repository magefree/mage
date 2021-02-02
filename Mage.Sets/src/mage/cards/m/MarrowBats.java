
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class MarrowBats extends CardImpl {

    public MarrowBats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // Pay 4 life: Regenerate Marrow Bats.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new PayLifeCost(4)));
    }

    private MarrowBats(final MarrowBats card) {
        super(card);
    }

    @Override
    public MarrowBats copy() {
        return new MarrowBats(this);
    }
}
