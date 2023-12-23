
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class PursuitOfKnowledge extends CardImpl {

    public PursuitOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // If you would draw a card, you may put a study counter on Pursuit of Knowledge instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PursuitOfKnowledgeEffect()));

        // Remove three study counters from Pursuit of Knowledge, Sacrifice Pursuit of Knowledge: Draw seven cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(7),
            new RemoveCountersSourceCost(CounterType.STUDY.createInstance(3)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PursuitOfKnowledge(final PursuitOfKnowledge card) {
        super(card);
    }

    @Override
    public PursuitOfKnowledge copy() {
        return new PursuitOfKnowledge(this);
    }
}

class PursuitOfKnowledgeEffect extends ReplacementEffectImpl {

    public PursuitOfKnowledgeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may put a study counter on {this} instead";
    }

    private PursuitOfKnowledgeEffect(final PursuitOfKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public PursuitOfKnowledgeEffect copy() {
        return new PursuitOfKnowledgeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if(controller != null) {
            if(controller.chooseUse(Outcome.Benefit, "Add a study counter instead of drawing a card?", source, game)) {
                new AddCountersSourceEffect(CounterType.STUDY.createInstance()).apply(game, source);
                return true;
            }
        }
        return false;
    }
}
