package mage.cards.v;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ValorsReachTagTeamToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValorsReachTagTeam extends CardImpl {

    public ValorsReachTagTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setWhite(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Create two 3/2 red and white Warrior creature tokens with "Whenever this creature and at least one other creature token attack, put a +1/+1 counter on this creature."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ValorsReachTagTeamToken(), 2));
    }

    private ValorsReachTagTeam(final ValorsReachTagTeam card) {
        super(card);
    }

    @Override
    public ValorsReachTagTeam copy() {
        return new ValorsReachTagTeam(this);
    }
}
