package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutOntoBattlefieldTappedRestInHandEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class TroopOfPonies extends CardImpl {

    public TroopOfPonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}, {T}, Sacrifice this creature: Search your library for up to two basic land cards, reveal them, put one onto the battlefield tapped and the other into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(
            new SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS)
            ),
            new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TroopOfPonies(final TroopOfPonies card) {
        super(card);
    }

    @Override
    public TroopOfPonies copy() {
        return new TroopOfPonies(this);
    }
}
