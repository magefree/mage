
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author Styxo
 */
public final class SpireOfIndustry extends CardImpl {

    public SpireOfIndustry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay 1 life: Add one mana of any color. Activate this ability only if you control an artifact.
        Ability ability = new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent("you control an artifact")));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private SpireOfIndustry(final SpireOfIndustry card) {
        super(card);
    }

    @Override
    public SpireOfIndustry copy() {
        return new SpireOfIndustry(this);
    }
}
