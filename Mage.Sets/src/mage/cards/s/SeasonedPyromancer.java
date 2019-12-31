package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.YoungPyromancerElementalToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedPyromancer extends CardImpl {

    public SeasonedPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Seasoned Pyromancer enters the battlefield, discard two cards, then draw two cards. For each nonland card discarded this way, create a 1/1 red Elemental creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SeasonedPyromancerEffect()));

        // {3}{R}{R}, Exile Seasoned Pyromancer from your graveyard: Create two 1/1 red Elemental creature tokens.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new CreateTokenEffect(new YoungPyromancerElementalToken(), 2),
                new ManaCostsImpl("{3}{R}{R}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SeasonedPyromancer(final SeasonedPyromancer card) {
        super(card);
    }

    @Override
    public SeasonedPyromancer copy() {
        return new SeasonedPyromancer(this);
    }
}

class SeasonedPyromancerEffect extends OneShotEffect {

    SeasonedPyromancerEffect() {
        super(Outcome.Benefit);
        staticText = "discard two cards, then draw two cards. " +
                "For each nonland card discarded this way, " +
                "create a 1/1 red Elemental creature token.";
    }

    private SeasonedPyromancerEffect(final SeasonedPyromancerEffect effect) {
        super(effect);
    }

    @Override
    public SeasonedPyromancerEffect copy() {
        return new SeasonedPyromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int nonlands = 0;
        int toDiscard = Math.min(player.getHand().size(), 2);
        if (toDiscard > 0) {
            TargetCard target = new TargetCardInHand(toDiscard, StaticFilters.FILTER_CARD);
            if (player.choose(Outcome.Discard, player.getHand(), target, game)) {
                Cards cards = new CardsImpl(target.getTargets());
                for (Card card : cards.getCards(game)) {
                    if (player.discard(card, source, game) && !card.isLand()) {
                        nonlands++;
                    }
                }
            }
        }
        player.drawCards(2, game);
        if (nonlands > 0) {
            return new CreateTokenEffect(new YoungPyromancerElementalToken(), nonlands).apply(game, source);
        }
        return true;
    }
}