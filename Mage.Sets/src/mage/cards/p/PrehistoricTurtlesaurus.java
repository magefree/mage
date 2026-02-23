package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrehistoricTurtlesaurus extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("you control a creature with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public PrehistoricTurtlesaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast if you control a creature with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(hint));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private PrehistoricTurtlesaurus(final PrehistoricTurtlesaurus card) {
        super(card);
    }

    @Override
    public PrehistoricTurtlesaurus copy() {
        return new PrehistoricTurtlesaurus(this);
    }
}
