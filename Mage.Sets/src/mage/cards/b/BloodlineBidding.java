package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodlineBidding extends CardImpl {

    public BloodlineBidding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Choose a creature type. Return all creature cards of the chosen type from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new BloodlineBiddingEffect());
    }

    private BloodlineBidding(final BloodlineBidding card) {
        super(card);
    }

    @Override
    public BloodlineBidding copy() {
        return new BloodlineBidding(this);
    }
}

class BloodlineBiddingEffect extends OneShotEffect {

    BloodlineBiddingEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Return all creature cards " +
                "of the chosen type from your graveyard to the battlefield";
    }

    private BloodlineBiddingEffect(final BloodlineBiddingEffect effect) {
        super(effect);
    }

    @Override
    public BloodlineBiddingEffect copy() {
        return new BloodlineBiddingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(game, source);
        player.choose(outcome, choice, game);
        SubType subType = SubType.byDescription(choice.getChoice());
        if (subType == null) {
            return false;
        }
        game.informPlayers(CardUtil.getSourceLogName(game, source) + ": " + player.getLogName() + " chooses " + subType);
        Cards cards = new CardsImpl(player.getGraveyard().getCards(game));
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));
        cards.removeIf(uuid -> !game.getCard(uuid).hasSubtype(subType, game));
        return !cards.isEmpty() && player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
