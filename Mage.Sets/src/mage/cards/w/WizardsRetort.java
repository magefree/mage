
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author Will
 */
public final class WizardsRetort extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Wizard");

    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
    }

    public WizardsRetort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Wizard's Retort costs {1} less to cast if you control a Wizard.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(1, new PermanentsOnTheBattlefieldCondition(filter)));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public WizardsRetort(final WizardsRetort card) {
        super(card);
    }

    @Override
    public WizardsRetort copy() {
        return new WizardsRetort(this);
    }
}
