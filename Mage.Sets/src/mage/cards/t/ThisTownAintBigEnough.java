package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThisTownAintBigEnough extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(StaticFilters.FILTER_CONTROLLED_A_PERMANENT);

    public ThisTownAintBigEnough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {3} less to cast if it targets a permanent you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return up to two target nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(0, 2));
    }

    private ThisTownAintBigEnough(final ThisTownAintBigEnough card) {
        super(card);
    }

    @Override
    public ThisTownAintBigEnough copy() {
        return new ThisTownAintBigEnough(this);
    }
}
