package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenOfTheHunt extends CardImpl {

    public OmenOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Omen of the Hunt enters the battlefield, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), true));

        // {2}{G}, Sacrifice Omen of the Hunt: Scry 2.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OmenOfTheHunt(final OmenOfTheHunt card) {
        super(card);
    }

    @Override
    public OmenOfTheHunt copy() {
        return new OmenOfTheHunt(this);
    }
}
