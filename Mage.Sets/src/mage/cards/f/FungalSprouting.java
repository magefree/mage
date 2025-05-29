
package mage.cards.f;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FungalSprouting extends CardImpl {

    public FungalSprouting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Create X 1/1 green Saproling creature tokens, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private FungalSprouting(final FungalSprouting card) {
        super(card);
    }

    @Override
    public FungalSprouting copy() {
        return new FungalSprouting(this);
    }
}
