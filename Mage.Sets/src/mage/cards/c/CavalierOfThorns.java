package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class CavalierOfThorns extends CardImpl {

    private static final FilterCard filter = new FilterCard("another card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CavalierOfThorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Cavalier of Thorns enters the battlefield, reveal the top five cards of your library.
        // Put a land card from among them onto the battlefield and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.BATTLEFIELD, PutCards.GRAVEYARD, false)));

        // When Cavalier of Thorns dies, you may exile it. If you do, put another target card from your graveyard on top of your library.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new PutOnLibraryTargetEffect(true), new ExileSourceFromGraveCost()
        ).setText("you may exile it. If you do, put another target card from your graveyard on top of your library."));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private CavalierOfThorns(final CavalierOfThorns card) {
        super(card);
    }

    @Override
    public CavalierOfThorns copy() {
        return new CavalierOfThorns(this);
    }
}
