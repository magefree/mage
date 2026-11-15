
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.OtherThanEnteringPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author Viserion
 */
public final class ValakutTheMoltenPinnacle extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MOUNTAIN, "a Mountain");
    private static final FilterPermanent otherMountainsFilter
            = new FilterPermanent(SubType.MOUNTAIN, "you control at least five other Mountains");

    static {
        otherMountainsFilter.add(OtherThanEnteringPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(otherMountainsFilter, ComparisonType.OR_GREATER, 5);

    public ValakutTheMoltenPinnacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Valakut, the Molten Pinnacle enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever a Mountain you control enters, if you control at least five other Mountains, you may have Valakut, the Molten Pinnacle deal 3 damage to any target.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(3), filter, true
        ).withInterveningIf(condition);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private ValakutTheMoltenPinnacle(final ValakutTheMoltenPinnacle card) {
        super(card);
    }

    @Override
    public ValakutTheMoltenPinnacle copy() {
        return new ValakutTheMoltenPinnacle(this);
    }
}
