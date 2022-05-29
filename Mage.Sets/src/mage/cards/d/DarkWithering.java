package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class DarkWithering extends CardImpl {

    public DarkWithering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}{B}");

        // Destroy target nonblack creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());

        // Madness {B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}")));
    }

    private DarkWithering(final DarkWithering card) {
        super(card);
    }

    @Override
    public DarkWithering copy() {
        return new DarkWithering(this);
    }
}
