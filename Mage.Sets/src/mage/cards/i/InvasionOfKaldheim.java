package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKaldheim extends CardImpl {

    public InvasionOfKaldheim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{3}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.p.PyreOfTheWorldTree.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Kaldheim enters the battlefield, exile all cards from your hand, then draw that many cards. Until the end of your next turn, you may play cards exiled this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfKaldheimEffect()));
    }

    private InvasionOfKaldheim(final InvasionOfKaldheim card) {
        super(card);
    }

    @Override
    public InvasionOfKaldheim copy() {
        return new InvasionOfKaldheim(this);
    }
}

class InvasionOfKaldheimEffect extends OneShotEffect {

    InvasionOfKaldheimEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from your hand, then draw that many cards. " +
                "Until the end of your next turn, you may play cards exiled this way";
    }

    private InvasionOfKaldheimEffect(final InvasionOfKaldheimEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfKaldheimEffect copy() {
        return new InvasionOfKaldheimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        player.moveCards(cards, Zone.EXILED, source, game);
        player.drawCards(cards.size(), source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, false);
        }
        return true;
    }
}
