package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ContestedWarZone extends CardImpl {

    public ContestedWarZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Whenever a creature deals combat damage to you, that creature's controller gains control of Contested War Zone.
        this.addAbility(new ContestedWarZoneAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Attacking creatures get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ContestedWarZone(final ContestedWarZone card) {
        super(card);
    }

    @Override
    public ContestedWarZone copy() {
        return new ContestedWarZone(this);
    }

}

class ContestedWarZoneAbility extends TriggeredAbilityImpl {

    public ContestedWarZoneAbility() {
        super(Zone.BATTLEFIELD, new ContestedWarZoneEffect());
    }

    public ContestedWarZoneAbility(final ContestedWarZoneAbility ability) {
        super(ability);
    }

    @Override
    public ContestedWarZoneAbility copy() {
        return new ContestedWarZoneAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (damageEvent.getPlayerId().equals(getControllerId()) && permanent != null && permanent.isCreature(game)) {
                game.getState().setValue(getSourceId().toString(), permanent.getControllerId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, that creature's controller gains control of {this}";
    }

}

class ContestedWarZoneEffect extends ContinuousEffectImpl {

    public ContestedWarZoneEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public ContestedWarZoneEffect(final ContestedWarZoneEffect effect) {
        super(effect);
    }

    @Override
    public ContestedWarZoneEffect copy() {
        return new ContestedWarZoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        UUID controllerId = (UUID) game.getState().getValue(source.getSourceId().toString());
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game, source);
        } else {
            discard();
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of {this}";
    }
}
