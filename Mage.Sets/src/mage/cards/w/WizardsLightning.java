package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Will
 */
public final class WizardsLightning extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Wizard");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public WizardsLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Wizard's Lightning costs {2} less to cast if you control a Wizard.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ConditionHint(condition, "You control a Wizard"));
        this.addAbility(ability);

        // Wizard's Lightning deals 3 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
    }

    private WizardsLightning(final WizardsLightning card) {
        super(card);
    }

    @Override
    public WizardsLightning copy() {
        return new WizardsLightning(this);
    }
}
