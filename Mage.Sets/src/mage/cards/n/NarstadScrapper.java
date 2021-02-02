
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class NarstadScrapper extends CardImpl {

    public NarstadScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Narstad Scrapper gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2)));
    }

    private NarstadScrapper(final NarstadScrapper card) {
        super(card);
    }

    @Override
    public NarstadScrapper copy() {
        return new NarstadScrapper(this);
    }
}
