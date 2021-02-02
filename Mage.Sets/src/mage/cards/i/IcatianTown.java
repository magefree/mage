
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CitizenToken;

/**
 *
 * @author Quercitron
 */
public final class IcatianTown extends CardImpl {

    public IcatianTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}");


        // Create four 1/1 white Citizen creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CitizenToken(), 4));
    }

    private IcatianTown(final IcatianTown card) {
        super(card);
    }

    @Override
    public IcatianTown copy() {
        return new IcatianTown(this);
    }
}
