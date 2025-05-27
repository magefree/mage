package mage.cards.c;

import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ChocoboToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoboRacetrack extends CardImpl {

    public ChocoboRacetrack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}{G}");

        // Landfall -- Whenever a land you control enters, create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new ChocoboToken())));
    }

    private ChocoboRacetrack(final ChocoboRacetrack card) {
        super(card);
    }

    @Override
    public ChocoboRacetrack copy() {
        return new ChocoboRacetrack(this);
    }
}
