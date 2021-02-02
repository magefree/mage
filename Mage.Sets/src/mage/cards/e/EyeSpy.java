
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class EyeSpy extends CardImpl {

    public EyeSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Look at the top card of target player's library. You may put that card into their graveyard.
        this.getSpellAbility().addEffect(new LookLibraryTopCardTargetPlayerEffect(1, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EyeSpy(final EyeSpy card) {
        super(card);
    }

    @Override
    public EyeSpy copy() {
        return new EyeSpy(this);
    }
}
