package mage.cards.g;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.GiantBadgerToken;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class Greensleeves extends CardImpl {

    public Greensleeves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GREENSLEEVES);
        this.setStartingLoyalty(5);

        // +2: Create a Giant Badger token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new GiantBadgerToken()), 2));

        // −3: Mill three cards. Put all permanent cards from among them into your hand.
        this.addAbility(new LoyaltyAbility(new GreensleevesMillEffect(), -3));

        // −8: Until end of turn, creatures you control have base power and toughness 8/8 and gain trample.
        LoyaltyAbility ability = new LoyaltyAbility(
            new SetBasePowerToughnessAllEffect(
                8, 8,
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
            ).setText("Until end of turn, creatures you control have base power and toughness 8/8"),
            -8
        );
        ability.addEffect(
            new GainAbilityAllEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
            ).setText("and gain trample")
        );
        this.addAbility(ability);

        // Greensleeves can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private Greensleeves(final Greensleeves card) {
        super(card);
    }

    @Override
    public Greensleeves copy() {
        return new Greensleeves(this);
    }
}

class GreensleevesMillEffect extends OneShotEffect {

    GreensleevesMillEffect() {
        super(Outcome.DrawCard);
        staticText = "mill three cards. Put all permanent cards from among them into your hand";
    }

    private GreensleevesMillEffect(final GreensleevesMillEffect effect) {
        super(effect);
    }

    @Override
    public GreensleevesMillEffect copy() {
        return new GreensleevesMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(3, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        for (Card card : cards.getCards(game)) {
            if (!card.isPermanent(game)) {
                cards.remove(card);
            }
        }
        player.moveCardsToHandWithInfo(cards, source, game, true);
        return true;
    }
}
