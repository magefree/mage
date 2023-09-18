
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class BringerOfTheBlackDawn extends CardImpl {

    public BringerOfTheBlackDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}{B}");
        this.subtype.add(SubType.BRINGER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may pay {W}{U}{B}{R}{G} rather than pay Bringer of the Black Dawn's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, you may pay 2 life. If you do, search your library for a card, then shuffle your library and put that card on top of it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(), false), new PayLifeCost(2)), TargetController.YOU, false);
        this.addAbility(ability);
    }

    private BringerOfTheBlackDawn(final BringerOfTheBlackDawn card) {
        super(card);
    }

    @Override
    public BringerOfTheBlackDawn copy() {
        return new BringerOfTheBlackDawn(this);
    }
}
