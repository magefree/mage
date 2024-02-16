package mage.cards.d;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisruptionProtocol extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("untapped artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public DisruptionProtocol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // As an additional cost to cast this spell, tap an untapped artifact you control or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
                "tap an untapped artifact you control or pay {1}", new TapTargetCost(new TargetControlledPermanent(filter)),
                new GenericManaCost(1)
        ));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DisruptionProtocol(final DisruptionProtocol card) {
        super(card);
    }

    @Override
    public DisruptionProtocol copy() {
        return new DisruptionProtocol(this);
    }
}
