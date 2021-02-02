
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LoneFox

 */
public final class EyeblightMassacre extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Non-Elf creatures");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
    }

    public EyeblightMassacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Non-Elf creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false));
    }

    private EyeblightMassacre(final EyeblightMassacre card) {
        super(card);
    }

    @Override
    public EyeblightMassacre copy() {
        return new EyeblightMassacre(this);
    }
}
