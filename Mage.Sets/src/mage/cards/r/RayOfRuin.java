package mage.cards.r;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RayOfRuin extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, Vehicle, or nonbasic land");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate(),
                Predicates.and(
                        Predicates.not(SuperType.BASIC.getPredicate()),
                        CardType.LAND.getPredicate()
                )
        ));
    }

    public RayOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Exile target creature, Vehicle, or nonbasic land. Scry 1.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RayOfRuin(final RayOfRuin card) {
        super(card);
    }

    @Override
    public RayOfRuin copy() {
        return new RayOfRuin(this);
    }
}
