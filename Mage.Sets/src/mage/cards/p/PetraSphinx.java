
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com & L_J
 */
public final class PetraSphinx extends CardImpl {

    public PetraSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{W}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {tap}: Target player chooses a card name, then reveals the top card of their library. If that card has the chosen name, that player puts it into their hand. If it doesn't, the player puts it into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PetraSphinxEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public PetraSphinx(final PetraSphinx card) {
        super(card);
    }

    @Override
    public PetraSphinx copy() {
        return new PetraSphinx(this);
    }

}

class PetraSphinxEffect extends OneShotEffect {

    public PetraSphinxEffect() {
        super(Outcome.DrawCard);
        staticText = "Target player chooses a card name, then reveals the top card of their library. If that card has the chosen name, that player puts it into their hand. If it doesn't, the player puts it into their graveyard";
    }

    public PetraSphinxEffect(final PetraSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && player != null) {
            if (player.getLibrary().hasCards()) {
                Choice cardChoice = new ChoiceImpl();
                cardChoice.setChoices(CardRepository.instance.getNames());
                cardChoice.setMessage("Name a card");
                if (!player.choose(Outcome.DrawCard, cardChoice, game)) {
                    return false;
                }
                String cardName = cardChoice.getChoice();
                game.informPlayers(CardUtil.createObjectRealtedWindowTitle(source, game, null) + ", player: " + player.getLogName() + ", named: [" + cardName + ']');
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    player.revealCards(source, cards, game);
                    if (card.getName().equals(cardName)) {
                        player.moveCards(cards, Zone.HAND, source, game);
                    } else {
                        player.moveCards(cards, Zone.GRAVEYARD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PetraSphinxEffect copy() {
        return new PetraSphinxEffect(this);
    }

}
