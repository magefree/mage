
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class EyeblightsEnding extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Elf creature");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
    }

    public EyeblightsEnding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{2}{B}");
        this.subtype.add(SubType.ELF);

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private EyeblightsEnding(final EyeblightsEnding card) {
        super(card);
    }

    @Override
    public EyeblightsEnding copy() {
        return new EyeblightsEnding(this);
    }
}
