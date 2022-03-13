package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeliverUntoEvil extends CardImpl {

    public DeliverUntoEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose up to four target cards in your graveyard. If you control a Bolas planeswalker, return those cards to your hand. Otherwise, an opponent chooses two of them. Leave the chosen cards in your graveyard and put the rest into your hand.
        this.getSpellAbility().addEffect(new DeliverUntoEvilEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 4));

        // Exile Deliver Unto Evil.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private DeliverUntoEvil(final DeliverUntoEvil card) {
        super(card);
    }

    @Override
    public DeliverUntoEvil copy() {
        return new DeliverUntoEvil(this);
    }
}

class DeliverUntoEvilEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.BOLAS);
    private static final FilterCard filter2 = new FilterCard("cards (to leave in the graveyard)");

    DeliverUntoEvilEffect() {
        super(Outcome.Benefit);
        staticText = "Choose up to four target cards in your graveyard. If you control a Bolas planeswalker, " +
                "return those cards to your hand. Otherwise, an opponent chooses two of them. " +
                "Leave the chosen cards in your graveyard and put the rest into your hand.<br>";
    }

    private DeliverUntoEvilEffect(final DeliverUntoEvilEffect effect) {
        super(effect);
    }

    @Override
    public DeliverUntoEvilEffect copy() {
        return new DeliverUntoEvilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(source.getTargets().get(0).getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        if (!game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game).isEmpty()) {
            return player.moveCards(cards, Zone.HAND, source, game);
        }
        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.setNotTarget(true);
        if (!player.choose(outcome, targetOpponent, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInGraveyard(Math.min(2, cards.size()), filter2);
        if (!opponent.choose(outcome, cards, targetCard, game)) {
            return false;
        }
        cards.removeAll(targetCard.getTargets());
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}