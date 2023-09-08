package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
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
        // If the card a player revealed is the card they named, that player puts it into their hand.
        // If it's not, that player puts it on the bottom of their library.
        this.addAbility(new AttacksTriggeredAbility(new ConundrumSphinxEffect(), false));
    }

    private ConundrumSphinx(final ConundrumSphinx card) {
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
        staticText = "each player chooses a card name. Then each player reveals the top card of their library. " +
                "If the card a player revealed has the name they chose, that player puts it into their hand. " +
                "If it doesn't, that player puts it on the bottom of their library";
    }

    private ConundrumSphinxEffect(final ConundrumSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || !player.getLibrary().hasCards()) {
                continue;
            }
            String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(player, game, source, false);
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            Cards cards = new CardsImpl(card);
            player.revealCards(source, cards, game);
            if (CardUtil.haveSameNames(card, cardName, game)) {
                player.moveCards(cards, Zone.HAND, source, game);
            } else {
                player.putCardsOnBottomOfLibrary(cards, game, source, false);
            }
        }
        return true;
    }

    @Override
    public ConundrumSphinxEffect copy() {
        return new ConundrumSphinxEffect(this);
    }
}
