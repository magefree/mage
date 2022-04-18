
package mage.cards.j;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class JustTheWind extends CardImpl {

    public JustTheWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Madness {U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{U}")));
    }

    private JustTheWind(final JustTheWind card) {
        super(card);
    }

    @Override
    public JustTheWind copy() {
        return new JustTheWind(this);
    }
}
