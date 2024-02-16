
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EnchantmentBirdToken;

/**
 *
 * @author LevelX2
 */
public final class RiseOfEagles extends CardImpl {

    public RiseOfEagles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Create two 2/2 blue Bird enchantment creature tokens with flying. Scry 1.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EnchantmentBirdToken(), 2));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private RiseOfEagles(final RiseOfEagles card) {
        super(card);
    }

    @Override
    public RiseOfEagles copy() {
        return new RiseOfEagles(this);
    }
}
