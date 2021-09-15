package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class WakeToSlaughter extends CardImpl {

    public WakeToSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Choose up to two target creature cards in your graveyard. An opponent chooses one of them. Return that card to your hand. Return the other to the battlefield under your control. It gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addEffect(new WakeToSlaughterEffect());

        // Flashback {4}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{B}{R}")));

    }

    private WakeToSlaughter(final WakeToSlaughter card) {
        super(card);
    }

    @Override
    public WakeToSlaughter copy() {
        return new WakeToSlaughter(this);
    }
}

class WakeToSlaughterEffect extends OneShotEffect {

    public WakeToSlaughterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose up to two target creature cards in your graveyard. " +
                "An opponent chooses one of them. " +
                "Return that card to your hand. " +
                "Return the other to the battlefield under your control. " +
                "It gains haste. " +
                "Exile it at the beginning of the next end step.";
    }

    public WakeToSlaughterEffect(final mage.cards.w.WakeToSlaughterEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.w.WakeToSlaughterEffect copy() {
        return new mage.cards.w.WakeToSlaughterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && source.getTargets() != null) {
            Cards pickedCards = new CardsImpl(source.getTargets().get(0).getTargets());
            player.revealCards(staticText, pickedCards, game);
            Card cardToHand;
            if (pickedCards.size() == 1) {
                cardToHand = pickedCards.getRandom(game);
            } else {
                Player opponent;
                Set<UUID> opponents = game.getOpponents(player.getId());
                if (opponents.size() == 1) {
                    opponent = game.getPlayer(opponents.iterator().next());
                } else {
                    Target targetOpponent = new TargetOpponent(true);
                    player.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                }

                TargetCard target = new TargetCard(1, Zone.GRAVEYARD, new FilterCard());
                target.withChooseHint("Card to go to opponent's hand (other goes to battlefield)");
                opponent.chooseTarget(outcome, pickedCards, target, source, game);
                cardToHand = game.getCard(target.getFirstTarget());
            }
            if (cardToHand != null) {
                player.moveCards(cardToHand, Zone.HAND, source, game);
                pickedCards.remove(cardToHand);
            }
            player.moveCards(pickedCards, Zone.BATTLEFIELD, source, game);
            return true;
        }

        return false;
    }
}
