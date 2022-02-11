
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class RingOfMaruf extends CardImpl {

    public RingOfMaruf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {5}, {Tap}, Exile Ring of Maruf: The next time you would draw a card this turn, instead choose a card you own from outside the game and put it into your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RingOfMarufEffect(), new ManaCostsImpl("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addHint(OpenSideboardHint.instance);
        this.addAbility(ability);
    }

    private RingOfMaruf(final RingOfMaruf card) {
        super(card);
    }

    @Override
    public RingOfMaruf copy() {
        return new RingOfMaruf(this);
    }
}

class RingOfMarufEffect extends ReplacementEffectImpl {

    public RingOfMarufEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next time you would draw a card this turn, instead choose a card you own from outside the game and put it into your hand.";
    }

    public RingOfMarufEffect(final RingOfMarufEffect effect) {
        super(effect);
    }

    @Override
    public RingOfMarufEffect copy() {
        return new RingOfMarufEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new WishEffect().apply(game, source);
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
