package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
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
        this.staticText = "Choose up to two target creature cards in your graveyard." +
                "An opponent chooses one of them. Return that card to your hand. Return the other to the battlefield under your control." +
                "It gains haste. Exile it at the beginning of the next end step.";
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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD, game));
            TargetCard target = new TargetCard(2, 2, Zone.GRAVEYARD, StaticFilters.FILTER_CARD_CREATURE);
            if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                Cards pickedCards = new CardsImpl(target.getTargets());
                cards.removeAll(pickedCards);
                if (!pickedCards.isEmpty()) {
                    controller.revealCards(staticText, pickedCards, game);
                    Card cardToBattlefield;
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target targetOpponent = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    }
                    TargetCard target2 = new TargetCard(1, Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                    opponent.chooseTarget(outcome, pickedCards, target2, source, game);
                    cardToBattlefield = game.getCard(target2.getFirstTarget());

                    if (cardToBattlefield != null) {
                        controller.moveCards(cardToBattlefield, Zone.BATTLEFIELD, source, game);
                        pickedCards.remove(cardToBattlefield);
                    }
                    controller.moveCards(pickedCards, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
