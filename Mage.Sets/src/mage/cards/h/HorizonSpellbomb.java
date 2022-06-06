
package mage.cards.h;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class HorizonSpellbomb extends CardImpl {

    public HorizonSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {2}, {tap}, Sacrifice Horizon Spellbomb: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        // When Horizon Spellbomb is put into a graveyard from the battlefield, you may pay {G}. If you do, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{G}"))));
    }

    private HorizonSpellbomb(final HorizonSpellbomb card) {
        super(card);
    }

    @Override
    public HorizonSpellbomb copy() {
        return new HorizonSpellbomb(this);
    }
}
