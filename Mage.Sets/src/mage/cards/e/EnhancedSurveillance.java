package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

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
                new ShuffleYourGraveyardIntoLibraryEffect(), new ExileSourceCost()
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

    EnhancedSurveillanceReplacementEffect() {
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
