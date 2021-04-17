package mage.cards.s;

import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnowDay extends CardImpl {

    public SnowDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1).concatBy("<br>"));
    }

    private SnowDay(final SnowDay card) {
        super(card);
    }

    @Override
    public SnowDay copy() {
        return new SnowDay(this);
    }
}
