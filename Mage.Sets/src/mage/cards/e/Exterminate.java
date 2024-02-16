package mage.cards.e;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Exterminate extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.DALEK, "untapped Dalek you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public Exterminate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Replicate--Tap an untapped Dalek you control.
        this.addAbility(new ReplicateAbility(new TapTargetCost(new TargetControlledPermanent(filter))));

        // Destroy target creature. Its controller loses 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Exterminate(final Exterminate card) {
        super(card);
    }

    @Override
    public Exterminate copy() {
        return new Exterminate(this);
    }
}
