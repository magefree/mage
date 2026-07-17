package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RunBehind extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(StaticFilters.FILTER_AN_ATTACKING_CREATURE);

    public RunBehind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // This spell costs {1} less to cast if it targets an attacking creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RunBehind(final RunBehind card) {
        super(card);
    }

    @Override
    public RunBehind copy() {
        return new RunBehind(this);
    }
}
