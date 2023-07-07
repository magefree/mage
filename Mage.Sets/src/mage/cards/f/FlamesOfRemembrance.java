
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class FlamesOfRemembrance extends CardImpl {

    public FlamesOfRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // At the beggining of your upkeep, you may exile a card from your graveyard. If you do, put a lore counter on Flames of Remembrance.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ExileFromGraveCost(new TargetCardInYourGraveyard()), null, true), TargetController.YOU, false));

        // Sacrifice Flames of Remembrance: Exile top X cards of your library, where X is the number of lore counters on Flames of Remembrance. Until end of turn you play cards exiled this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlamesOfRemembranceExileEffect(new CountersSourceCount(CounterType.LORE)), new SacrificeSourceCost()));
    }

    private FlamesOfRemembrance(final FlamesOfRemembrance card) {
        super(card);
    }

    @Override
    public FlamesOfRemembrance copy() {
        return new FlamesOfRemembrance(this);
    }
}

class FlamesOfRemembranceExileEffect extends OneShotEffect {

    private CountersSourceCount amount;

    public FlamesOfRemembranceExileEffect(CountersSourceCount amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = "Exile top X cards of your library, where X is the number of lore counters on {this}. " +
            "Until end of turn you may play cards exiled this way";
    }

    public FlamesOfRemembranceExileEffect(final FlamesOfRemembranceExileEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public FlamesOfRemembranceExileEffect copy() {
        return new FlamesOfRemembranceExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, amount.calculate(game, source, this)));
            if (!cards.isEmpty()) {
                CardUtil.exileCardsAndMakePlayable(game, source, cards.getCards(game), Duration.EndOfTurn);
            }
            return true;
        }
        return false;
    }
}
