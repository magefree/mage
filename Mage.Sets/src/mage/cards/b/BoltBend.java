package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.NumberOfTargetsPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoltBend extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public BoltBend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // This spell costs {3} less to cast if you control a creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new SpellCostReductionSourceEffect(3, FerociousCondition.instance)
        ).setRuleAtTheTop(true));

        // Change the target of target spell or ability with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private BoltBend(final BoltBend card) {
        super(card);
    }

    @Override
    public BoltBend copy() {
        return new BoltBend(this);
    }
}
