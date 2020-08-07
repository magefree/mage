package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondersEnclave extends CardImpl {

    public BondersEnclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Draw a card. Activate this ability only if you control a creature with power 4 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new GenericManaCost(3), FerociousCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BondersEnclave(final BondersEnclave card) {
        super(card);
    }

    @Override
    public BondersEnclave copy() {
        return new BondersEnclave(this);
    }
}
