package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CompelledDuel extends CardImpl {

    public CompelledDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature gets +3/+3 until end of turn and must be blocked this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addEffect(new MustBeBlockedByAtLeastOneTargetEffect().setText("and must be blocked this turn if able"));
    }

    private CompelledDuel(final CompelledDuel card) {
        super(card);
    }

    @Override
    public CompelledDuel copy() {
        return new CompelledDuel(this);
    }
}
