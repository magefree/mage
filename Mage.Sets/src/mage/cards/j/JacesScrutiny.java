
package mage.cards.j;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class JacesScrutiny extends CardImpl {

    public JacesScrutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Target creature gets -4/-0 until end of turn.
        getSpellAbility().addEffect(new BoostTargetEffect(-4, -0, Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Investigate
        getSpellAbility().addEffect(new InvestigateEffect());
    }

    private JacesScrutiny(final JacesScrutiny card) {
        super(card);
    }

    @Override
    public JacesScrutiny copy() {
        return new JacesScrutiny(this);
    }
}
