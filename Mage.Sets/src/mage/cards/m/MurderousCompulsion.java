
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class MurderousCompulsion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public MurderousCompulsion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Destroy target tapped creature.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Madness {1}{B} <i>(If you discard card, discard it into exile. When you do, cast it for its madness cost or put it into your graveyard.)<i>
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private MurderousCompulsion(final MurderousCompulsion card) {
        super(card);
    }

    @Override
    public MurderousCompulsion copy() {
        return new MurderousCompulsion(this);
    }
}
