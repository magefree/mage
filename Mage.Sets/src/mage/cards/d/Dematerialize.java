
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.TargetPermanent;

/**
 *
 * @author cbt33
 */
public final class Dematerialize extends CardImpl {

    public Dematerialize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Return target permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}{U}")));
    }

    private Dematerialize(final Dematerialize card) {
        super(card);
    }

    @Override
    public Dematerialize copy() {
        return new Dematerialize(this);
    }
}
