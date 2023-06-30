package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BitterDownfall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public BitterDownfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // This spell costs {3} less to cast if it targets a creature that was dealt damage this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Destroy target creature. Its controller loses 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BitterDownfall(final BitterDownfall card) {
        super(card);
    }

    @Override
    public BitterDownfall copy() {
        return new BitterDownfall(this);
    }
}
