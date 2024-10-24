package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

public class BloodsproutTalisman extends CardImpl {

    public BloodsproutTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{G}");

        // Bloodsprout Talisman enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Pay 1 life: Choose a nonland card in your hand. It perpetually gains “This spell costs {1} less to cast.”
        Ability spellCostReductionAbility = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1));
        Ability ability = new SimpleActivatedAbility(
                new ChooseACardInYourHandItPerpetuallyGainsEffect(spellCostReductionAbility, StaticFilters.FILTER_CARD_A_NON_LAND),
                new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private BloodsproutTalisman(final BloodsproutTalisman card) {
        super(card);
    }

    @Override
    public BloodsproutTalisman copy() {
        return new BloodsproutTalisman(this);
    }
}
