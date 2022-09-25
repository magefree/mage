package mage.cards.j;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoinForces extends CardImpl {

    public JoinForces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Untap up to two target creatures. They each get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("they each get +2/+2 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private JoinForces(final JoinForces card) {
        super(card);
    }

    @Override
    public JoinForces copy() {
        return new JoinForces(this);
    }
}
