package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PlayerToRightGainsControlOfSourceEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HithlainRope extends CardImpl {

    public HithlainRope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Hithlain Rope can't be sacrificed.
        this.addAbility(new SimpleStaticAbility(new CantBeSacrificedSourceEffect()));

        // {1}, {T}: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle. The player to your right gains control of Hithlain Rope.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new PlayerToRightGainsControlOfSourceEffect());
        this.addAbility(ability);

        // {2}, {T}: Draw a card. The player to your right gains control of Hithlain Rope.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new PlayerToRightGainsControlOfSourceEffect());
        this.addAbility(ability);

    }

    private HithlainRope(final HithlainRope card) {
        super(card);
    }

    @Override
    public HithlainRope copy() {
        return new HithlainRope(this);
    }
}
