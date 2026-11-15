package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class WrathOfTheBloodmane extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("you control a legendary creature");
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary creature");

    public WrathOfTheBloodmane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell costs {1} less to cast if you control a legendary creature.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // Wrath of the Bloodmane deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private WrathOfTheBloodmane(final WrathOfTheBloodmane card) {
        super(card);
    }

    @Override
    public WrathOfTheBloodmane copy() {
        return new WrathOfTheBloodmane(this);
    }
}
