package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeizeOpportunity extends CardImpl {

    public SeizeOpportunity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one --
        // * Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn));

        // * Up to two target creatures each get +2/+1 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(2, 1)).addTarget(new TargetCreaturePermanent(0, 2)));
    }

    private SeizeOpportunity(final SeizeOpportunity card) {
        super(card);
    }

    @Override
    public SeizeOpportunity copy() {
        return new SeizeOpportunity(this);
    }
}
