package mage.cards.s;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpectralReserves extends CardImpl {

    public SpectralReserves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Create two 1/1 white Spirit creature tokens with flying. You gain 2 life.
        Effect effect = new CreateTokenEffect(new SpiritWhiteToken(), 2);
        effect.setText("Create two 1/1 white Spirit creature tokens with flying");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private SpectralReserves(final SpectralReserves card) {
        super(card);
    }

    @Override
    public SpectralReserves copy() {
        return new SpectralReserves(this);
    }
}
