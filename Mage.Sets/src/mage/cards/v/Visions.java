
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class Visions extends CardImpl {

    public Visions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // Look at the top five cards of target player's library. You may then have that player shuffle that library.
        this.getSpellAbility().addEffect(new LookLibraryTopCardTargetPlayerEffect(5, false, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Visions(final Visions card) {
        super(card);
    }

    @Override
    public Visions copy() {
        return new Visions(this);
    }
}
