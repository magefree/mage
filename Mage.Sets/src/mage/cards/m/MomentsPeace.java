
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;

/**
 *
 * @author Plopman
 */
public final class MomentsPeace extends CardImpl {

    public MomentsPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));

        // Flashback {2}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{G}")));
    }

    private MomentsPeace(final MomentsPeace card) {
        super(card);
    }

    @Override
    public MomentsPeace copy() {
        return new MomentsPeace(this);
    }
}
