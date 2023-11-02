
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class AshesOfTheAbhorrent extends CardImpl {

    public AshesOfTheAbhorrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Players can't cast spells from graveyards or activate abilities of cards in graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshesOfTheAbhorrentEffect()));

        // Whenever a creature dies, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(1), false));
    }

    private AshesOfTheAbhorrent(final AshesOfTheAbhorrent card) {
        super(card);
    }

    @Override
    public AshesOfTheAbhorrent copy() {
        return new AshesOfTheAbhorrent(this);
    }
}

class AshesOfTheAbhorrentEffect extends ContinuousRuleModifyingEffectImpl {

    public AshesOfTheAbhorrentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Players can't cast spells from graveyards or activate abilities of cards in graveyards";
    }

    private AshesOfTheAbhorrentEffect(final AshesOfTheAbhorrentEffect effect) {
        super(effect);
    }

    @Override
    public AshesOfTheAbhorrentEffect copy() {
        return new AshesOfTheAbhorrentEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Zone zone = game.getState().getZone(card.getId());
            if (zone != null && (zone == Zone.GRAVEYARD)) {
                return true;
            }
        }
        return false;
    }
}
