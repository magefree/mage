package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowSummoning extends CardImpl {

    public ShadowSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Create two tapped 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 2, true));
    }

    private ShadowSummoning(final ShadowSummoning card) {
        super(card);
    }

    @Override
    public ShadowSummoning copy() {
        return new ShadowSummoning(this);
    }
}
