package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author tiera3 - based on MerfolkLooter
 * note - draftmatters ability not implemented
 */
public final class DealBroker extends CardImpl {

    public DealBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
		// {t}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost()));
    }

    private DealBroker(final DealBroker card) {
        super(card);
    }

    @Override
    public DealBroker copy() {
        return new DealBroker(this);
    }

}
