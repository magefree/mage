package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class PiaAetherAscetic extends CardImpl {

    public PiaAetherAscetic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Pia enters, you may discard a card. If you do, search your library for an enchantment card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoIfCostPaid(
                new SearchLibraryPutInHandEffect(
                    new TargetCardInLibrary(StaticFilters.FILTER_CARD_ENCHANTMENT), true
                ),
                new DiscardCardCost()
            )
        ));
    }

    private PiaAetherAscetic(final PiaAetherAscetic card) {
        super(card);
    }

    @Override
    public PiaAetherAscetic copy() {
        return new PiaAetherAscetic(this);
    }
}
