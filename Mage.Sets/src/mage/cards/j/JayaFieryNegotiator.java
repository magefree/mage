package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.command.emblems.JayaFieryNegotiatorEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.token.MonkRedToken;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class JayaFieryNegotiator extends CardImpl {

    public JayaFieryNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);
        this.setStartingLoyalty(4);

        // +1: Create a 1/1 red Monk creature token with prowess.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new MonkRedToken()), 1));

        // −1: Exile the top two cards of your library. Choose one of them. You may play that card this turn.
        this.addAbility(new LoyaltyAbility(new JayaFieryNegotiatorExileEffect(), -1));

        // −2: Choose target creature an opponent controls. Whenever you attack this turn, Jaya, Fiery Negotiator deals damage equal to the number of attacking creatures to that creature.
        Ability ability = new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new JayaFieryNegotiatorTriggeredAbility()
        ).setText("choose target creature an opponent controls. Whenever you attack this turn, " +
                "{this} deals damage equal to the number of attacking creatures to that creature"), -2);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // −8: You get an emblem with "Whenever you cast a red instant or sorcery spell, copy it twice. You may choose new targets for the copies."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JayaFieryNegotiatorEmblem()), -8));
    }

    private JayaFieryNegotiator(final JayaFieryNegotiator card) {
        super(card);
    }

    @Override
    public JayaFieryNegotiator copy() {
        return new JayaFieryNegotiator(this);
    }
}

class JayaFieryNegotiatorExileEffect extends OneShotEffect {

    JayaFieryNegotiatorExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. Choose one of them. You may play that card this turn";
    }

    private JayaFieryNegotiatorExileEffect(final JayaFieryNegotiatorExileEffect effect) {
        super(effect);
    }

    @Override
    public JayaFieryNegotiatorExileEffect copy() {
        return new JayaFieryNegotiatorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        Card card;

        if (cards.size() == 0) {
            return false;
        } else if (cards.size() == 1) {
            card = cards.getRandom(game);
            player.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source), "Jaya, Fiery Negotiator");
        } else {
            Iterator<Card> iterator = cards.getCards(game).iterator();
            Card card1 = iterator.next();
            Card card2 = iterator.next();
            card = player.chooseUse(
                    outcome, "Choose a card to play this turn", null,
                    card1.getName(), card2.getName(), source, game
            ) ? card1 : card2;

            Set<Card> exileCards = new HashSet<>(2);
            exileCards.add(card1);
            exileCards.add(card2);
            player.moveCardsToExile(exileCards, source, game, true, CardUtil.getExileZoneId(game, source), "Jaya, Fiery Negotiator");
        }
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}

class JayaFieryNegotiatorTriggeredAbility extends DelayedTriggeredAbility {

    private static final DynamicValue xValue = new AttackingCreatureCount();

    JayaFieryNegotiatorTriggeredAbility() {
        super(new DamageTargetEffect(xValue), Duration.EndOfTurn, false, false);
    }

    private JayaFieryNegotiatorTriggeredAbility(final JayaFieryNegotiatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JayaFieryNegotiatorTriggeredAbility copy() {
        return new JayaFieryNegotiatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getCombat().getAttackingPlayerId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever you attack this turn, {this} deals damage equal to the number of attacking creatures to that creature.";
    }
}
