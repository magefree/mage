package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerfectIntimidation extends CardImpl {

    public PerfectIntimidation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose one or both --
        // * Target opponent exiles two cards from their hand.
        this.getSpellAbility().addEffect(new PerfectIntimidationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Remove all counters from target creature.
        this.getSpellAbility().addMode(new Mode(new RemoveAllCountersPermanentTargetEffect())
                .addTarget(new TargetCreaturePermanent()));
    }

    private PerfectIntimidation(final PerfectIntimidation card) {
        super(card);
    }

    @Override
    public PerfectIntimidation copy() {
        return new PerfectIntimidation(this);
    }
}

class PerfectIntimidationEffect extends OneShotEffect {

    PerfectIntimidationEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles two cards from their hand";
    }

    private PerfectIntimidationEffect(final PerfectIntimidationEffect effect) {
        super(effect);
    }

    @Override
    public PerfectIntimidationEffect copy() {
        return new PerfectIntimidationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        switch (player.getHand().size()) {
            case 0:
                return false;
            case 1:
            case 2:
                cards.addAll(player.getHand());
                break;
            default:
                TargetCard target = new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS);
                player.choose(Outcome.Discard, player.getHand(), target, source, game);
                cards.addAll(target.getTargets());
        }
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}
