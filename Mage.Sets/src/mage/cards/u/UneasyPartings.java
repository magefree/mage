package mage.cards.u;

import java.util.UUID;
import mage.abilities.condition.Condition;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class UneasyPartings extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("an attacking nontoken creature");

    static {
        filter.add(Predicates.not(TokenPredicate.FALSE));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public UneasyPartings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // This spell costs {1} less to cast if it targets an attacking nontoken creature.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UneasyPartings(final UneasyPartings card) {
        super(card);
    }

    @Override
    public UneasyPartings copy() {
        return new UneasyPartings(this);
    }
}
