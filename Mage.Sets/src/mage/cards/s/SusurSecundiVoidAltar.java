package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SusurSecundiVoidAltar extends CardImpl {

    public SusurSecundiVoidAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLANET);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 12+
        // {1}{B}, {T}, Pay 2 life, Sacrifice a creature: Draw cards equal to the sacrificed creature's power. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(SacrificeCostCreaturesPower.instance)
                        .setText("draw cards equal to the sacrificed creature's power"),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(new StationLevelAbility(12).withLevelAbility(ability));
    }

    private SusurSecundiVoidAltar(final SusurSecundiVoidAltar card) {
        super(card);
    }

    @Override
    public SusurSecundiVoidAltar copy() {
        return new SusurSecundiVoidAltar(this);
    }
}
