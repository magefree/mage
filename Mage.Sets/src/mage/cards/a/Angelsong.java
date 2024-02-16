

package mage.cards.a;

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
 * @author BetaSteward_at_googlemail.com
 */
public final class Angelsong extends CardImpl {

    public Angelsong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Angelsong(final Angelsong card) {
        super(card);
    }

    @Override
    public Angelsong copy() {
        return new Angelsong(this);
    }

}
