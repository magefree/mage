package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerWordKill extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("non-Angel, non-Demon, non-Devil, non-Dragon creature");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
        filter.add(Predicates.not(SubType.DEVIL.getPredicate()));
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public PowerWordKill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Destroy target non-Angel, non-Demon, non-Devil, non-Dragon creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private PowerWordKill(final PowerWordKill card) {
        super(card);
    }

    @Override
    public PowerWordKill copy() {
        return new PowerWordKill(this);
    }
}
