
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ExposeEvil extends CardImpl {

    public ExposeEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Tap up to two target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Investigate (Create a colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\")
        this.getSpellAbility().addEffect(new InvestigateEffect().concatBy("<br>"));
    }

    private ExposeEvil(final ExposeEvil card) {
        super(card);
    }

    @Override
    public ExposeEvil copy() {
        return new ExposeEvil(this);
    }
}
