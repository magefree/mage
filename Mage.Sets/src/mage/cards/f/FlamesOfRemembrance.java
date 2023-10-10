
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public final class FlamesOfRemembrance extends CardImpl {

    public FlamesOfRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // At the beggining of your upkeep, you may exile a card from your graveyard. If you do, put a lore counter on Flames of Remembrance.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ExileFromGraveCost(new TargetCardInYourGraveyard()), null, true), TargetController.YOU, false));

        // Sacrifice Flames of Remembrance: Exile top X cards of your library, where X is the number of lore counters on Flames of Remembrance. Until end of turn you play cards exile this way.
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
        this.staticText = "Exile top X cards of your library, where X is the number of lore counters on {this}. Until end of turn you play cards exile this way";
    }

    private FlamesOfRemembranceExileEffect(final FlamesOfRemembranceExileEffect effect) {
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
                controller.moveCardsToExile(cards.getCards(game), source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, ""));
                ContinuousEffect effect = new FlamesOfRemembranceMayPlayExiledEffect();
                effect.setTargetPointer(new FixedTargets(cards, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

}

class FlamesOfRemembranceMayPlayExiledEffect extends AsThoughEffectImpl {

    public FlamesOfRemembranceMayPlayExiledEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private FlamesOfRemembranceMayPlayExiledEffect(final FlamesOfRemembranceMayPlayExiledEffect effect) {
        super(effect);
    }

    @Override
    public FlamesOfRemembranceMayPlayExiledEffect copy() {
        return new FlamesOfRemembranceMayPlayExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId) && this.getTargetPointer().getTargets(game, source).contains(objectId);
    }
}
