
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author nantuko
 */
public final class PurifyTheGrave extends CardImpl {

    public PurifyTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Exile target card from a graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());

        // Flashback {W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{W}")));
    }

    private PurifyTheGrave(final PurifyTheGrave card) {
        super(card);
    }

    @Override
    public PurifyTheGrave copy() {
        return new PurifyTheGrave(this);
    }
}
