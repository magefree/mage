
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author LevelX2
 */
public final class ServoExhibition extends CardImpl {

    public ServoExhibition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Create two 1/1 colorless Servo artifact creature tokens.
        Effect effect = new CreateTokenEffect(new ServoToken(), 2);
        effect.setText("Create two 1/1 colorless Servo artifact creature tokens");
        this.getSpellAbility().addEffect(effect);
    }

    private ServoExhibition(final ServoExhibition card) {
        super(card);
    }

    @Override
    public ServoExhibition copy() {
        return new ServoExhibition(this);
    }
}
