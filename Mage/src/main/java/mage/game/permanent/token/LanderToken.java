package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class LanderToken extends TokenImpl {

    public static String getReminderText() {
        return "<i>(It's an artifact with \"{2}, {T}, Sacrifice this token: "
                + "Search your library for a basic land card, put it onto the "
                + "battlefield tapped, then shuffle.\")</i>";
    }

    public LanderToken() {
        super("Lander Token", "Lander token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.LANDER);

        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        ), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LanderToken(final LanderToken token) {
        super(token);
    }

    public LanderToken copy() {
        return new LanderToken(this);
    }
}
