package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiverwheelSweep extends CardImpl {

    public RiverwheelSweep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2/U}{2/R}{2/W}");

        // Tap target creature. Put three stun counters on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(3))
                .setText("put three stun counters on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        this.getSpellAbility().addEffect(new RiverwheelSweepEffect());
    }

    private RiverwheelSweep(final RiverwheelSweep card) {
        super(card);
    }

    @Override
    public RiverwheelSweep copy() {
        return new RiverwheelSweep(this);
    }
}

class RiverwheelSweepEffect extends OneShotEffect {

    RiverwheelSweepEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top two cards of your library. Choose one of them. " +
                "Until the end of your next turn, you may play that card";
        this.concatBy("<br>");
    }

    private RiverwheelSweepEffect(final RiverwheelSweepEffect effect) {
        super(effect);
    }

    @Override
    public RiverwheelSweepEffect copy() {
        return new RiverwheelSweepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.withNotTarget(true);
                player.choose(Outcome.DrawCard, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        CardUtil.makeCardPlayable(
                game, source, card, false,
                Duration.UntilEndOfYourNextTurn, false
        );
        return true;
    }
}
