
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.SetPlayerLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class BlessedWind extends CardImpl {

    public BlessedWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{W}{W}");

        // Target player's life total becomes 20.
        this.getSpellAbility().addEffect(new SetPlayerLifeTargetEffect(20));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BlessedWind(final BlessedWind card) {
        super(card);
    }

    @Override
    public BlessedWind copy() {
        return new BlessedWind(this);
    }
}
