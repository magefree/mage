
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author LevelX2
 */
public final class CallTheScions extends CardImpl {

    public CallTheScions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Create two 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken(), 2);
        effect.setText("create two 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}.\"");
        this.getSpellAbility().addEffect(effect);

    }

    private CallTheScions(final CallTheScions card) {
        super(card);
    }

    @Override
    public CallTheScions copy() {
        return new CallTheScions(this);
    }
}
