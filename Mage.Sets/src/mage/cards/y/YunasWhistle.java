package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YunasWhistle extends CardImpl {

    public YunasWhistle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");

        // Reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order. When you reveal a creature card this way, put X+1/+1 counters on target creature you control, where X is the mana value of that card.
        this.getSpellAbility().addEffect(new YunasWhistleEffect());
    }

    private YunasWhistle(final YunasWhistle card) {
        super(card);
    }

    @Override
    public YunasWhistle copy() {
        return new YunasWhistle(this);
    }
}

class YunasWhistleEffect extends OneShotEffect {

    YunasWhistleEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card into your hand and the rest on the bottom of your library in a random order. " +
                "When you reveal a creature card this way, put X +1/+1 counters on target creature you control, " +
                "where X is the mana value of that card";
    }

    private YunasWhistleEffect(final YunasWhistleEffect effect) {
        super(effect);
    }

    @Override
    public YunasWhistleEffect copy() {
        return new YunasWhistleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, game, source);
        player.revealCards(source, cards, game);
        if (card == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        player.moveCards(card, Zone.HAND, source, game);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(card.getManaValue())), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    private static Card getCard(Player player, Cards cards, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }
}
