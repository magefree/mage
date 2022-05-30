
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.FilterCard;

/**
 *
 * @author cbt33
 */
 
public final class AncestralTribute extends CardImpl {

    public AncestralTribute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");

                
        // You gain 2 life for each card in your graveyard.
	this.getSpellAbility().addEffect(new GainLifeEffect((new CardsInControllerGraveyardCount(new FilterCard(), 2))));

	// Flashback {9}{W}{W}{W}
	this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{9}{W}{W}{W}")));

        
    }

    private AncestralTribute(final AncestralTribute card) {
        super(card);
    }

    @Override
    public AncestralTribute copy() {
        return new AncestralTribute(this);
    }
}
