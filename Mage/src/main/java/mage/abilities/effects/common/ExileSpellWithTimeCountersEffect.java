package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public class ExileSpellWithTimeCountersEffect extends OneShotEffect {

    private final int counters;

    public ExileSpellWithTimeCountersEffect(int counters) {
        super(Outcome.Exile);
        this.counters = counters;
        this.staticText = "Exile {this} with " + CardUtil.numberToText(this.counters) + " time counters on it";
    }

    private ExileSpellWithTimeCountersEffect(final ExileSpellWithTimeCountersEffect effect) {
        super(effect);
        this.counters = effect.counters;
    }

    @Override
    public ExileSpellWithTimeCountersEffect copy() {
        return new ExileSpellWithTimeCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getStack().getSpell(source.getId()).getCard();
        if (controller == null || card == null) {
            return true;
        }
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        if (!card.isCopy() && controller.moveCardsToExile(card, source, game, true, exileId, "Suspended cards of " + controller.getName())) {
            card.addCounters(CounterType.TIME.createInstance(3), source.getControllerId(), source, game);
            game.informPlayers(controller.getLogName() + " exiles " + card.getLogName() + " with " + counters + " time counters on it");
        }
        return true;
    }
}
