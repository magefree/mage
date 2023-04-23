package mage.cards.p;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WillOfThePlaneswalkersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfTheGhosthunter extends CardImpl {

    public PathOfTheGhosthunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{W}");

        // Create X 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), ManacostVariableValue.REGULAR));

        // Will of the Planeswalkers -- Starting with you, each player votes for planeswalk or chaos. If planeswalk gets more votes, planeswalk. If chaos gets more votes or the vote is tied, chaos ensues.
        this.getSpellAbility().addEffect(new WillOfThePlaneswalkersEffect());
    }

    private PathOfTheGhosthunter(final PathOfTheGhosthunter card) {
        super(card);
    }

    @Override
    public PathOfTheGhosthunter copy() {
        return new PathOfTheGhosthunter(this);
    }
}
