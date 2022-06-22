
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox
 */
public final class TidalCourier extends CardImpl {

    private static final FilterCard filter = new FilterCard("Merfolk cards");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public TidalCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Tidal Courier enters the battlefield, reveal the top four cards of your library. Put all Merfolk cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(4, filter, Zone.LIBRARY)));
        // {3}{U}: Tidal Courier gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(),
                Duration.EndOfTurn), new ManaCostsImpl<>("{3}{U}")));
    }

    private TidalCourier(final TidalCourier card) {
        super(card);
    }

    @Override
    public TidalCourier copy() {
        return new TidalCourier(this);
    }
}
