package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LookoutsDispersal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Pirate");

    public LookoutsDispersal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Lookout's Dispersal costs {1} less to cast if you control a Pirate.

        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // Counter target spell unless its controller pays {4}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private LookoutsDispersal(final LookoutsDispersal card) {
        super(card);
    }

    @Override
    public LookoutsDispersal copy() {
        return new LookoutsDispersal(this);
    }
}
