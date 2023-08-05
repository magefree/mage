package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrugaCliffcharger extends CardImpl {

    private static final FilterCard filter = new FilterCard("a land or battle card");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public TrugaCliffcharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Truga Cliffcharger enters the battlefield, you may discard a card. If you do, search your library for a land or battle card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                ), new DiscardCardCost())
        ));
    }

    private TrugaCliffcharger(final TrugaCliffcharger card) {
        super(card);
    }

    @Override
    public TrugaCliffcharger copy() {
        return new TrugaCliffcharger(this);
    }
}
