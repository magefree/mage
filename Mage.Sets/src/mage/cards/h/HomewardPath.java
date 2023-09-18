package mage.cards.h;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainControlAllOwnedEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HomewardPath extends CardImpl {

    public HomewardPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Each player gains control of all creatures they own.
        this.addAbility(new SimpleActivatedAbility(new GainControlAllOwnedEffect(
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), new TapSourceCost()));
    }

    private HomewardPath(final HomewardPath card) {
        super(card);
    }

    @Override
    public HomewardPath copy() {
        return new HomewardPath(this);
    }
}
