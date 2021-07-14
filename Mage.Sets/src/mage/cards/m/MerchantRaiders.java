package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerchantRaiders extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PIRATE, "Pirate");

    public MerchantRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Merchant Raiders or another Pirate enters the battlefield under your control, tap up to one target creature. That creature doesn't untap during its controller's untap step for as long as you control Merchant Raiders.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TapTargetEffect("tap up to one target creature"),
                filter, false, true
        );
        ability.addEffect(new MerchantRaidersEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability, new MerchantRaidersWatcher());
    }

    private MerchantRaiders(final MerchantRaiders card) {
        super(card);
    }

    @Override
    public MerchantRaiders copy() {
        return new MerchantRaiders(this);
    }
}

class MerchantRaidersEffect extends ContinuousRuleModifyingEffectImpl {

    public MerchantRaidersEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public MerchantRaidersEffect(final MerchantRaidersEffect effect) {
        super(effect);
    }

    @Override
    public MerchantRaidersEffect copy() {
        return new MerchantRaidersEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP
                || event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Source must be on the battlefield (it's neccessary to check here because if as response to the enter
        // the battlefield triggered ability the source dies (or will be exiled), then the ZONE_CHANGE or LOST_CONTROL
        // event will happen before this effect is applied ever)
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Permanent) || !((Permanent) sourceObject).isControlledBy(source.getControllerId())) {
            discard();
            return false;
        }
        switch (event.getType()) {
            case ZONE_CHANGE:
                // end effect if source does a zone move
                if (!event.getTargetId().equals(source.getSourceId())) {
                    break;
                }
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                    discard();
                    return false;
                }
                break;
            case UNTAP:
                // prevent to untap the target creature
                if (game.getTurn().getStepType() != PhaseStep.UNTAP
                        || !event.getTargetId().equals(targetPointer.getFirst(game, source))) {
                    break;
                }
                Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                if (targetCreature != null) {
                    return targetCreature.isControlledBy(game.getActivePlayerId());
                } else {
                    discard();
                    return false;
                }
            case LOST_CONTROL:
                // end effect if source control is changed
                if (event.getTargetId().equals(source.getSourceId())) {
                    discard();
                    return false;
                }
                break;
        }
        return false;
    }
}

class MerchantRaidersWatcher extends Watcher {

    MerchantRaidersWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL
                && event.getPlayerId().equals(controllerId)
                && event.getTargetId().equals(sourceId)) {
            condition = true;
            game.replaceEvent(event);
            return;
        } else if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(sourceId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                condition = true;
                game.replaceEvent(event);
            }
        }
    }
}
