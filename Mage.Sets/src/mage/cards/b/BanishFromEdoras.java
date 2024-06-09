package mage.cards.b;

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
 * @author TheElk801
 */
public final class BanishFromEdoras extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public BanishFromEdoras(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // This spell costs {2} less to cast if it targets a tapped creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BanishFromEdoras(final BanishFromEdoras card) {
        super(card);
    }

    @Override
    public BanishFromEdoras copy() {
        return new BanishFromEdoras(this);
    }
}
