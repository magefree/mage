
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SliverToken;

/**
 *
 * @author jeffwadsworth
 */
public final class HiveStirrings extends CardImpl {

    public HiveStirrings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Create two 1/1 colorless Sliver creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SliverToken(), 2));
    }

    private HiveStirrings(final HiveStirrings card) {
        super(card);
    }

    @Override
    public HiveStirrings copy() {
        return new HiveStirrings(this);
    }
}
