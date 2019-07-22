
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward
 */
public final class GrafdiggersCage extends CardImpl {

    public GrafdiggersCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Creature cards can't enter the battlefield from graveyards or libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrafdiggersCageEffect()));

        // Players can't cast cards in graveyards or libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrafdiggersCageEffect2()));
    }

    public GrafdiggersCage(final GrafdiggersCage card) {
        super(card);
    }

    @Override
    public GrafdiggersCage copy() {
        return new GrafdiggersCage(this);
    }
}

class GrafdiggersCageEffect extends ContinuousRuleModifyingEffectImpl {

    public GrafdiggersCageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creature cards in graveyards and libraries can't enter the battlefield";
    }

    public GrafdiggersCageEffect(final GrafdiggersCageEffect effect) {
        super(effect);
    }

    @Override
    public GrafdiggersCageEffect copy() {
        return new GrafdiggersCageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.ZONE_CHANGE == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.BATTLEFIELD && (zEvent.getFromZone() == Zone.GRAVEYARD || zEvent.getFromZone() == Zone.LIBRARY)) {
            Card card = game.getCard(zEvent.getTargetId());
            if (card != null && card.isCreature()) {
                return true;
            }
        }
        return false;
    }

}

class GrafdiggersCageEffect2 extends ContinuousRuleModifyingEffectImpl {

    public GrafdiggersCageEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players can't cast spells from graveyards or libraries";
    }

    public GrafdiggersCageEffect2(final GrafdiggersCageEffect2 effect) {
        super(effect);
    }

    @Override
    public GrafdiggersCageEffect2 copy() {
        return new GrafdiggersCageEffect2(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Zone zone = game.getState().getZone(card.getId());
            if (zone == Zone.GRAVEYARD || zone == Zone.LIBRARY) {
                return true;
            }
        }
        return false;
    }

}
