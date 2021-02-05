package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NetherbornAltar extends CardImpl {

    public NetherbornAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        // {T}, Put a soul counter on Netherborn Altar: Put your commander into your hand from the command zone. Then you lose 3 life for each soul counter on Netherborn Altar.
        Ability ability = new SimpleActivatedAbility(new NetherbornAltarEffect(), new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(CounterType.SOUL.createInstance()));
        this.addAbility(ability);
    }

    private NetherbornAltar(final NetherbornAltar card) {
        super(card);
    }

    @Override
    public NetherbornAltar copy() {
        return new NetherbornAltar(this);
    }
}

class NetherbornAltarEffect extends OneShotEffect {

    NetherbornAltarEffect() {
        super(Outcome.Benefit);
        staticText = "Put your commander into your hand from the command zone. Then you lose 3 life for each soul counter on {this}.";
    }

    private NetherbornAltarEffect(final NetherbornAltarEffect effect) {
        super(effect);
    }

    @Override
    public NetherbornAltarEffect copy() {
        return new NetherbornAltarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<Card> commandersInCommandZone = new ArrayList<>(game.getCommanderCardsFromCommandZone(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER));
        if (commandersInCommandZone.size() == 1) {
            controller.moveCards(commandersInCommandZone.get(0), Zone.HAND, source, game);
        } else if (commandersInCommandZone.size() == 2) {
            Card firstCommander = commandersInCommandZone.get(0);
            Card secondCommander = commandersInCommandZone.get(1);
            if (controller.chooseUse(Outcome.ReturnToHand, "Return which commander to hand?", null, firstCommander.getName(), secondCommander.getName(), source, game)) {
                controller.moveCards(firstCommander, Zone.HAND, source, game);
            } else {
                controller.moveCards(secondCommander, Zone.HAND, source, game);
            }
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            int counterCount = permanent.getCounters(game).getCount(CounterType.SOUL);
            controller.loseLife(3 * counterCount, game, source, false);
        }
        return true;
    }
}