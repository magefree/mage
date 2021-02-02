
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * 
 * @author L_J
 */
public final class DealBroker extends CardImpl {

    public DealBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT, CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // TODO: Draft specific abilities not implemented
        // Draft Deal Broker face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Deal Broker face up.")));

        // Immediately after the draft, you may reveal a card in your card pool. Each other player may offer you one card in their card pool in exchange. You may accept any one offer.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Immediately after the draft, you may reveal a card in your card pool. "
                + "Each other player may offer you one card in their card pool in exchange. You may accept any one offer.")));

        // {T}: Draw a card, then discard a card.
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
