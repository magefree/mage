
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class SovereignsBite extends CardImpl {

    public SovereignsBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target player loses 3 life and you gain 3 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).setText("and you gain 3 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SovereignsBite(final SovereignsBite card) {
        super(card);
    }

    @Override
    public SovereignsBite copy() {
        return new SovereignsBite(this);
    }
}
