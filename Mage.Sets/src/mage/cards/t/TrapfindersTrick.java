package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class TrapfindersTrick extends CardImpl {

    public TrapfindersTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target player reveals their hand and discards all Trap cards.
        this.getSpellAbility().addEffect(new TrapfindersTrickEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TrapfindersTrick(final TrapfindersTrick card) {
        super(card);
    }

    @Override
    public TrapfindersTrick copy() {
        return new TrapfindersTrick(this);
    }
}

class TrapfindersTrickEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.TRAP.getPredicate());
    }

    TrapfindersTrickEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand and discards all Trap cards";
    }

    private TrapfindersTrickEffect(final TrapfindersTrickEffect effect) {
        super(effect);
    }

    @Override
    public TrapfindersTrickEffect copy() {
        return new TrapfindersTrickEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        player.discard(new CardsImpl(player.getHand().getCards(filter, game)), false, source, game);
        return true;
    }
}
