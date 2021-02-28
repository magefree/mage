package mage.cards.a;

import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AshesToAshes extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public AshesToAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Exile two target nonartifact creatures. Ashes to Ashes deals 5 damage to you.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
        this.getSpellAbility().addEffect(new DamageControllerEffect(5));
    }

    private AshesToAshes(final AshesToAshes card) {
        super(card);
    }

    @Override
    public AshesToAshes copy() {
        return new AshesToAshes(this);
    }
}
