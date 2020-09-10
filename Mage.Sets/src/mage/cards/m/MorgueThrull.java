
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MorgueThrull extends CardImpl {

    public MorgueThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Morgue Thrull: Put the top three cards of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsControllerEffect(3), new SacrificeSourceCost()));
    }

    public MorgueThrull(final MorgueThrull card) {
        super(card);
    }

    @Override
    public MorgueThrull copy() {
        return new MorgueThrull(this);
    }
}
