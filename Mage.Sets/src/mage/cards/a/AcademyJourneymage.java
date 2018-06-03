
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JRHerlehy
 */
public final class AcademyJourneymage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Wizard");

    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
    }

    public AcademyJourneymage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // This spell costs {1} less to cast if you control a Wizard.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(1, new PermanentsOnTheBattlefieldCondition(filter)));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // When Academy Journeymage enters the battlefield, return target creature an opponent controls to its owner's hand.
        ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    public AcademyJourneymage(final AcademyJourneymage card) {
        super(card);
    }

    @Override
    public AcademyJourneymage copy() {
        return new AcademyJourneymage(this);
    }
}
