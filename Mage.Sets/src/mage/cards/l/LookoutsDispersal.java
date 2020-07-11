package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
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

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public LookoutsDispersal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Lookout's Dispersal costs {1} less to cast if you control a Pirate.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ConditionHint(condition, "You control a Pirate"));
        this.addAbility(ability);

        // Counter target spell unless its controller pays {4}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public LookoutsDispersal(final LookoutsDispersal card) {
        super(card);
    }

    @Override
    public LookoutsDispersal copy() {
        return new LookoutsDispersal(this);
    }
}
