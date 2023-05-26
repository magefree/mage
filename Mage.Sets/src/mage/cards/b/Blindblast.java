package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class Blindblast extends CardImpl {

    public Blindblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Blindblast deals 1 damage to target creature. That creature can't block this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(
                Duration.EndOfTurn
        ).setText("That creature can't block this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Blindblast(final Blindblast card) {
        super(card);
    }

    @Override
    public Blindblast copy() {
        return new Blindblast(this);
    }
}
