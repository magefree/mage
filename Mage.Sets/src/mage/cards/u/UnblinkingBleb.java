
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class UnblinkingBleb extends CardImpl {

    public UnblinkingBleb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}")));
        
        // Whenever Unblinking Bleb or another permanent is turned face up, you may scry 2.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(new ScryEffect(2), new FilterPermanent("{this} or another permanent"), true));
    }

    private UnblinkingBleb(final UnblinkingBleb card) {
        super(card);
    }

    @Override
    public UnblinkingBleb copy() {
        return new UnblinkingBleb(this);
    }
}