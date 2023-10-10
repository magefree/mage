package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
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

    private PetraSphinx(final PetraSphinx card) {
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

    private PetraSphinxEffect(final PetraSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || player == null || !player.getLibrary().hasCards()) {
            return true;
        }
        String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(player, game, source, false);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        Cards cards = new CardsImpl(card);
        player.revealCards(source, cards, game);
        if (CardUtil.haveSameNames(card, cardName, game)) {
            player.moveCards(cards, Zone.HAND, source, game);
        } else {
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public PetraSphinxEffect copy() {
        return new PetraSphinxEffect(this);
    }
}
