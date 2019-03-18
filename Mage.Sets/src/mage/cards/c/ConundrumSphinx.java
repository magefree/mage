package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ConundrumSphinx extends CardImpl {

    public ConundrumSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Conundrum Sphinx attacks, each player names a card. Then each player reveals the top card of their library.
        // If the card a player revealed is the card he or she named, that player puts it into their hand.
        // If it's not, that player puts it on the bottom of their library.
        this.addAbility(new AttacksTriggeredAbility(new ConundrumSphinxEffect(), false));
    }

    public ConundrumSphinx(final ConundrumSphinx card) {
        super(card);
    }

    @Override
    public ConundrumSphinx copy() {
        return new ConundrumSphinx(this);
    }

}

class ConundrumSphinxEffect extends OneShotEffect {

    public ConundrumSphinxEffect() {
        super(Outcome.DrawCard);
        staticText = "each player names a card. Then each player reveals the top card of their library. If the card a player revealed is the card he or she named, that player puts it into their hand. If it's not, that player puts it on the bottom of their library";
    }

    public ConundrumSphinxEffect(final ConundrumSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            Players:
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        cardChoice.clearChoice();
                        cardChoice.setMessage("Name a card");
                        if (!player.choose(Outcome.DrawCard, cardChoice, game) && player.canRespond()) {
                            continue Players;
                        }
                        String cardName = cardChoice.getChoice();
                        game.informPlayers(sourceObject.getLogName() + ", player: " + player.getLogName() + ", named: [" + cardName + ']');
                        Card card = player.getLibrary().getFromTop(game);
                        if (card != null) {
                            Cards cards = new CardsImpl(card);
                            player.revealCards(source, player.getName(), cards, game);
                            if (CardUtil.haveSameNames(card.getName(), cardName)) {
                                player.moveCards(cards, Zone.HAND, source, game);
                            } else {
                                player.putCardsOnBottomOfLibrary(cards, game, source, false);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ConundrumSphinxEffect copy() {
        return new ConundrumSphinxEffect(this);
    }

}
