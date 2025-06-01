package mage.cards.o;

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
public final class OperaLoveSong extends CardImpl {

    public OperaLoveSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Exile the top two cards of your library. You may play those cards until your next end step.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(2, Duration.UntilYourNextEndStep));

        // * One or two target creatures each get +2/+0 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(2, 0)).addTarget(new TargetCreaturePermanent(1, 2)));
    }

    private OperaLoveSong(final OperaLoveSong card) {
        super(card);
    }

    @Override
    public OperaLoveSong copy() {
        return new OperaLoveSong(this);
    }
}
