package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Bewilder extends CardImpl {

    public Bewilder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target creature gets -3/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Bewilder(final Bewilder card) {
        super(card);
    }

    @Override
    public Bewilder copy() {
        return new Bewilder(this);
    }
}
