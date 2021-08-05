package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GildedAssaultCart extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    public GildedAssaultCart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Crew 2
        this.addAbility(new CrewAbility(2));

        // Sacrifice two Treasures: Return Gilded Assault Cart from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private GildedAssaultCart(final GildedAssaultCart card) {
        super(card);
    }

    @Override
    public GildedAssaultCart copy() {
        return new GildedAssaultCart(this);
    }
}
