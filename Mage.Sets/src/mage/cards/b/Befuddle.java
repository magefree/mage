
package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JRHerlehy
 */
public final class Befuddle extends CardImpl {

    public Befuddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target creature gets -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Befuddle(final Befuddle card) {
        super(card);
    }

    @Override
    public Befuddle copy() {
        return new Befuddle(this);
    }
}
