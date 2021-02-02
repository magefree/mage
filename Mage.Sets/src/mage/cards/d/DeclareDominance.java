package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DeclareDominance extends CardImpl {

    public DeclareDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Target creature gets +3/+3 until end of turn. All creatures able to block it this turn do so.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn)
                .setText("All creatures able to block it this turn do so.")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeclareDominance(final DeclareDominance card) {
        super(card);
    }

    @Override
    public DeclareDominance copy() {
        return new DeclareDominance(this);
    }
}
