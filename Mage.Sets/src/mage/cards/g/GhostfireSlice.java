package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GhostfireSlice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a multicolored permanent");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    private static final Condition condition = new OpponentControlsPermanentCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "An opponent controls a multicolored permanent");

    public GhostfireSlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");


        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // This spell costs {2} less to cast if an opponent controls a multicolored permanent.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // Ghostfire Slice deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private GhostfireSlice(final GhostfireSlice card) {
        super(card);
    }

    @Override
    public GhostfireSlice copy() {
        return new GhostfireSlice(this);
    }
}
