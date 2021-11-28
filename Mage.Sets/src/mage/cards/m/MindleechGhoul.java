package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindleechGhoul extends CardImpl {

    public MindleechGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Mindleech Ghoul exploits a creature, each opponent exiles a card from their hand.
        this.addAbility(new ExploitCreatureTriggeredAbility(new MindleechGhoulEffect()));
    }

    private MindleechGhoul(final MindleechGhoul card) {
        super(card);
    }

    @Override
    public MindleechGhoul copy() {
        return new MindleechGhoul(this);
    }
}

class MindleechGhoulEffect extends OneShotEffect {

    MindleechGhoulEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles a card from their hand";
    }

    private MindleechGhoulEffect(final MindleechGhoulEffect effect) {
        super(effect);
    }

    @Override
    public MindleechGhoulEffect copy() {
        return new MindleechGhoulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null || opponent.getHand().isEmpty()) {
                continue;
            }
            TargetCard target = new TargetCardInHand();
            opponent.choose(Outcome.Discard, opponent.getHand(), target, game);
            cards.add(game.getCard(target.getFirstTarget()));
        }
        return controller.moveCards(cards, Zone.EXILED, source, game);
    }
}
