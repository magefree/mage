package mage.cards.f;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallajiExcavation extends CardImpl {

    public FallajiExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Create three tapped Powerstone tokens. You gain 3 life.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 3, true));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private FallajiExcavation(final FallajiExcavation card) {
        super(card);
    }

    @Override
    public FallajiExcavation copy() {
        return new FallajiExcavation(this);
    }
}
