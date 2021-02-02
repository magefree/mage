
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author jeffwadsworth
 */
public final class FungalSprouting extends CardImpl {

    public FungalSprouting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // create X 1/1 green Saproling creature tokens, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), GreatestPowerAmongControlledCreaturesValue.instance));
    }

    private FungalSprouting(final FungalSprouting card) {
        super(card);
    }

    @Override
    public FungalSprouting copy() {
        return new FungalSprouting(this);
    }
}
