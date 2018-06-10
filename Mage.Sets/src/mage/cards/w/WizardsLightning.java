
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetAnyTarget;
/**
 *
 * @author Will
 */
public final class WizardsLightning extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Wizard");

    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
    }

    public WizardsLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Wizard's Lightning costs {2} less to cast if you control a Wizard.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(2, new PermanentsOnTheBattlefieldCondition(filter)));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Wizard's Lightning deals 3 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
    }

    public WizardsLightning(final WizardsLightning card) {
        super(card);
    }

    @Override
    public WizardsLightning copy() {
        return new WizardsLightning(this);
    }
}
