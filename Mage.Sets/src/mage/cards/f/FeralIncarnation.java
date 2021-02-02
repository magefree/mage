
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author LevelX2
 */
public final class FeralIncarnation extends CardImpl {

    public FeralIncarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Create three 3/3 green Beast creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken(), 3));
    }

    private FeralIncarnation(final FeralIncarnation card) {
        super(card);
    }

    @Override
    public FeralIncarnation copy() {
        return new FeralIncarnation(this);
    }
}
