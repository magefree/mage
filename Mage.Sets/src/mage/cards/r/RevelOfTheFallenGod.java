
package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.XenagosSatyrToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RevelOfTheFallenGod extends CardImpl {

    public RevelOfTheFallenGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}{G}{G}");

        // Create four 2/2 red and green Satyr creature tokens with haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new XenagosSatyrToken(), 4));

    }

    private RevelOfTheFallenGod(final RevelOfTheFallenGod card) {
        super(card);
    }

    @Override
    public RevelOfTheFallenGod copy() {
        return new RevelOfTheFallenGod(this);
    }
}
