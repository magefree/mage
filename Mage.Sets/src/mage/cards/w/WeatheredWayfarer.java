package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WeatheredWayfarer extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);

    public WeatheredWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}: Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library. Activate this ability only if an opponent controls more lands than you.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true
                ), new ManaCostsImpl<>("{W}"), condition
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WeatheredWayfarer(final WeatheredWayfarer card) {
        super(card);
    }

    @Override
    public WeatheredWayfarer copy() {
        return new WeatheredWayfarer(this);
    }
}
