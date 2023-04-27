
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class SilentDeparture extends CardImpl {

    public SilentDeparture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // Flashback {4}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{U}")));
    }

    private SilentDeparture(final SilentDeparture card) {
        super(card);
    }

    @Override
    public SilentDeparture copy() {
        return new SilentDeparture(this);
    }
}
