package mage.cards.p;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.WillOfThePlaneswalkersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfTheEnigma extends CardImpl {

    public PathOfTheEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Target player draws four cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Will of the Planeswalkers -- Starting with you, each player votes for planeswalk or chaos. If planeswalk gets more votes, planeswalk. If chaos gets more votes or the vote is tied, chaos ensues.
        this.getSpellAbility().addEffect(new WillOfThePlaneswalkersEffect());
    }

    private PathOfTheEnigma(final PathOfTheEnigma card) {
        super(card);
    }

    @Override
    public PathOfTheEnigma copy() {
        return new PathOfTheEnigma(this);
    }
}
