package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Bladebrand extends CardImpl {

    public Bladebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gains deathtouch until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Bladebrand(final Bladebrand card) {
        super(card);
    }

    @Override
    public Bladebrand copy() {
        return new Bladebrand(this);
    }
}
