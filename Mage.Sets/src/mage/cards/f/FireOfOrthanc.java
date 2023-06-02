package mage.cards.f;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireOfOrthanc extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without flying");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public FireOfOrthanc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy target artifact or land. Creatures without flying can't block this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter2, Duration.EndOfTurn));
    }

    private FireOfOrthanc(final FireOfOrthanc card) {
        super(card);
    }

    @Override
    public FireOfOrthanc copy() {
        return new FireOfOrthanc(this);
    }
}
