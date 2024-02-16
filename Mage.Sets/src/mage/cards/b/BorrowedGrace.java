
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class BorrowedGrace extends CardImpl {

    public BorrowedGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Escalate {1}{W}
        this.addAbility(new EscalateAbility(new ManaCostsImpl<>("{1}{W}")));

        // Choose one or both &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Creatures you control get +2/+0 until end of turn.;
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));

        // Creatures you control get +0/+2 until end of turn.
        Mode mode = new Mode(new BoostControlledEffect(0, 2, Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    private BorrowedGrace(final BorrowedGrace card) {
        super(card);
    }

    @Override
    public BorrowedGrace copy() {
        return new BorrowedGrace(this);
    }
}
