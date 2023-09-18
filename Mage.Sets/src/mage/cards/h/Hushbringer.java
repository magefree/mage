package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hushbringer extends CardImpl {

    public Hushbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Creatures entering the battlefield or dying don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(new HushbringerEffect()));
    }

    private Hushbringer(final Hushbringer card) {
        super(card);
    }

    @Override
    public Hushbringer copy() {
        return new Hushbringer(this);
    }
}

class HushbringerEffect extends ContinuousRuleModifyingEffectImpl {

    HushbringerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Creatures entering the battlefield or dying don't cause abilities to trigger";
    }

    private HushbringerEffect(final HushbringerEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if (ability == null || ability.getAbilityType() != AbilityType.TRIGGERED) {
            return false;
        }
        Permanent permanent;
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                permanent = ((EntersTheBattlefieldEvent) event).getTarget();
                break;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
                if (!zEvent.isDiesEvent()) {
                    return false;
                }
                permanent = zEvent.getTarget();
                break;
            default:
                return false;
        }

        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        return (((TriggeredAbility) ability).checkTrigger(event, game));
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject enteringObject = game.getObject(event.getSourceId());
        MageObject sourceObject = game.getObject(source);
        Ability ability = (Ability) getValue("targetAbility");
        if (enteringObject == null || sourceObject == null || ability == null) {
            return null;
        }
        MageObject abilitObject = game.getObject(ability.getSourceId());
        if (abilitObject == null) {
            return null;
        }
        return sourceObject.getLogName() + " prevented ability of "
                + abilitObject.getLogName() + " from triggering.";
    }

    @Override
    public HushbringerEffect copy() {
        return new HushbringerEffect(this);
    }
}
