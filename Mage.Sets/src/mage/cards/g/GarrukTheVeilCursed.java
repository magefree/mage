package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WolfTokenWithDeathtouch;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GarrukTheVeilCursed extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public GarrukTheVeilCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.color.setGreen(true);
        this.color.setBlack(true);

        // +1 : Create a 1/1 black Wolf creature token with deathtouch.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfTokenWithDeathtouch()), 1));

        // -1 : Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        StaticFilters.FILTER_CARD_CREATURE_A
                ), true),
                null,
                new SacrificeTargetCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                )),
                false
        ), -1));

        // -3 : Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Ability ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control gain trample"), -3);
        ability.addEffect(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("and get +X/+X until end of turn, where X is the number of creature cards in your graveyard"));
        this.addAbility(ability);
    }

    private GarrukTheVeilCursed(final GarrukTheVeilCursed card) {
        super(card);
    }

    @Override
    public GarrukTheVeilCursed copy() {
        return new GarrukTheVeilCursed(this);
    }
}
