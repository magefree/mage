package mage.cards.q;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class QuicksandWhirlpool extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public QuicksandWhirlpool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{W}");

        // This spell costs {3} less to cast if it targets a tapped creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private QuicksandWhirlpool(final QuicksandWhirlpool card) {
        super(card);
    }

    @Override
    public QuicksandWhirlpool copy() {
        return new QuicksandWhirlpool(this);
    }
}
