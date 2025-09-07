package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class TerrificTeamUp extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a permanent with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a permanent with mana value 4 or greater");

    public TerrificTeamUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");
        

        // This spell costs {2} less to cast if you control a permanent with mana value 4 or greater.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // One or two target creatures you control each get +1/+0 until end of turn. They each deal damage equal to their power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(false));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(1, 2).setTargetTag(1).withChooseHint("boost and deal damage"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent().setTargetTag(3));
    }

    private TerrificTeamUp(final TerrificTeamUp card) {
        super(card);
    }

    @Override
    public TerrificTeamUp copy() {
        return new TerrificTeamUp(this);
    }
}


