package mage.cards.w;

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
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author weirddan455
 */
public final class WeatheredRunestone extends CardImpl {

    public WeatheredRunestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Nonland permanent cards in graveyards and libraries can't enter the battlefield.
        this.addAbility(new SimpleStaticAbility(new WeatheredRunestoneEffect()));

        // Players can't cast spells from graveyards or libraries.
        this.addAbility(new SimpleStaticAbility(new WeatheredRunestoneEffect2()));
    }

    private WeatheredRunestone(final WeatheredRunestone card) {
        super(card);
    }

    @Override
    public WeatheredRunestone copy() {
        return new WeatheredRunestone(this);
    }
}

class WeatheredRunestoneEffect extends ContinuousRuleModifyingEffectImpl {

    public WeatheredRunestoneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Nonland permanent cards in graveyards and libraries can't enter the battlefield";
    }

    private WeatheredRunestoneEffect(final WeatheredRunestoneEffect effect) {
        super(effect);
    }

    @Override
    public WeatheredRunestoneEffect copy() {
        return new WeatheredRunestoneEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.ZONE_CHANGE == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.BATTLEFIELD && (zEvent.getFromZone() == Zone.GRAVEYARD || zEvent.getFromZone() == Zone.LIBRARY)) {
            Card card = game.getCard(zEvent.getTargetId());
            return card != null && !card.isLand(game) && card.isPermanent(game);
        }
        return false;
    }
}

class WeatheredRunestoneEffect2 extends ContinuousRuleModifyingEffectImpl {

    public WeatheredRunestoneEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players can't cast spells from graveyards or libraries";
    }

    private WeatheredRunestoneEffect2(final WeatheredRunestoneEffect2 effect) {
        super(effect);
    }

    @Override
    public WeatheredRunestoneEffect2 copy() {
        return new WeatheredRunestoneEffect2(this);
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
            return zone == Zone.GRAVEYARD || zone == Zone.LIBRARY;
        }
        return false;
    }
}
