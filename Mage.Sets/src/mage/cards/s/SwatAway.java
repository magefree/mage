package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.filter.common.FilterSpellOrPermanent;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwatAway extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreatureAttackingYou("a creature is attacking you")
    );
    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or creature");

    static {
        filter.getPermanentFilter().add(CardType.CREATURE.getPredicate());
    }

    public SwatAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // This spell costs {2} less to cast if a creature is attacking you.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // The owner of target spell or creature puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));
    }

    private SwatAway(final SwatAway card) {
        super(card);
    }

    @Override
    public SwatAway copy() {
        return new SwatAway(this);
    }
}
