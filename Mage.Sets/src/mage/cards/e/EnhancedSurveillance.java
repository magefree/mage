package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
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
 * @author TheElk801
 */
public final class EnhancedSurveillance extends CardImpl {

    public EnhancedSurveillance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // You may look at an additional two cards each time you surveil.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new EnhancedSurveillanceReplacementEffect()
        ));

        // Exile Enhanced Surveillance: Shuffle your graveyard into your library.
        this.addAbility(new SimpleActivatedAbility(
                new EnhancedSurveillanceShuffleEffect(), new ExileSourceCost()
        ));
    }

    private EnhancedSurveillance(final EnhancedSurveillance card) {
        super(card);
    }

    @Override
    public EnhancedSurveillance copy() {
        return new EnhancedSurveillance(this);
    }
}

class EnhancedSurveillanceReplacementEffect extends ReplacementEffectImpl {

    public EnhancedSurveillanceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may look at an additional "
                + "two cards each time you surveil.";
    }

    private EnhancedSurveillanceReplacementEffect(final EnhancedSurveillanceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EnhancedSurveillanceReplacementEffect copy() {
        return new EnhancedSurveillanceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SURVEIL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 2);
        return false;
    }
}

class EnhancedSurveillanceShuffleEffect extends OneShotEffect {

    public EnhancedSurveillanceShuffleEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle your graveyard into your library";
    }

    private EnhancedSurveillanceShuffleEffect(final EnhancedSurveillanceShuffleEffect effect) {
        super(effect);
    }

    @Override
    public EnhancedSurveillanceShuffleEffect copy() {
        return new EnhancedSurveillanceShuffleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Card card : controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
