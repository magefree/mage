package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class InventorsFair extends CardImpl {

    public InventorsFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, if you control three or more artifacts, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1))
                .withInterveningIf(MetalcraftCondition.instance));

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}, Sacrifice Inventors' Fair: Search your library for an artifact card, reveal it, put it into your hand, then shuffle your library.
        // Activate this ability only if you control threeor more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT), true
        ), new GenericManaCost(4), MetalcraftCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(MetalcraftHint.instance));
    }

    private InventorsFair(final InventorsFair card) {
        super(card);
    }

    @Override
    public InventorsFair copy() {
        return new InventorsFair(this);
    }
}
