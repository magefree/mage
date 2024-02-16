
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Backfir3
 */
public final class Lull extends CardImpl {

    public Lull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        //Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Lull(final Lull card) {
        super(card);
    }

    @Override
    public Lull copy() {
        return new Lull(this);
    }
}
