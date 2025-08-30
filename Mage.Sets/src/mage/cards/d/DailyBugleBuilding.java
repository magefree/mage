package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DailyBugleBuilding extends CardImpl {

    public DailyBugleBuilding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Smear Campaign -- {1}, {T}: Target legendary creature gains menace until end of turn. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new GainAbilityTargetEffect(new MenaceAbility(false)), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_LEGENDARY));
        this.addAbility(ability.withFlavorWord("Smear Campaign"));
    }

    private DailyBugleBuilding(final DailyBugleBuilding card) {
        super(card);
    }

    @Override
    public DailyBugleBuilding copy() {
        return new DailyBugleBuilding(this);
    }
}
