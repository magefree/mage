package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.Spirit32Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuintoriusHistoryChaser extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT, "Spirits you control");

    public QuintoriusHistoryChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.QUINTORIUS);
        this.setStartingLoyalty(5);

        // Whenever one or more cards leave your graveyard, create a 3/2 red and white Spirit creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new Spirit32Token())));

        // +1: You may discard a card. If you do, draw two cards, then mill a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2), new DiscardCardCost()
        ).addEffect(new MillCardsControllerEffect(1).concatBy(", then")), 1));

        // -4: Spirits you control gain double strike and vigilance until end of turn.
        Ability ability = new LoyaltyAbility(new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("Spirits you control gain double strike"), -4);
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and vigilance until end of turn"));
        this.addAbility(ability);

        // Quintorius, History Chaser can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private QuintoriusHistoryChaser(final QuintoriusHistoryChaser card) {
        super(card);
    }

    @Override
    public QuintoriusHistoryChaser copy() {
        return new QuintoriusHistoryChaser(this);
    }
}
