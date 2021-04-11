package mage.cards.d;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class DevouringTendrils extends CardImpl {

    public static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public DevouringTendrils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control. When the permanent you don't control dies this turn, you gain 2 life.
        Effect effect = new DamageWithPowerFromOneToAnotherTargetEffect();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        TargetPermanent target = new TargetPermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

        this.getSpellAbility().addEffect(new DevouringTendrilsEffect());
    }

    private DevouringTendrils(final DevouringTendrils card) {
        super(card);
    }

    @Override
    public DevouringTendrils copy() {
        return new DevouringTendrils(this);
    }
}

class DevouringTendrilsEffect extends OneShotEffect {

    DevouringTendrilsEffect() {
        super(Outcome.Benefit);
        staticText = "when the permanent you don't control dies this turn, you gain 2 life";
    }

    DevouringTendrilsEffect(DevouringTendrilsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = source.getTargets().stream()
            .filter(t -> t.getTargetTag() == 2)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Was expecting to find a target with tag 2 but found none"));
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            game.addEffect(new DevouringTendrilsReplacementEffect(new MageObjectReference(permanent, game)), source);
        }
        return true;
    }

    @Override
    public DevouringTendrilsEffect copy() {
        return new DevouringTendrilsEffect(this);
    }
}

class DevouringTendrilsReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference objectRef;

    DevouringTendrilsReplacementEffect(MageObjectReference objectRef) {
        super(Duration.EndOfTurn, Outcome.Benefit);
        this.objectRef = objectRef;
    }

    DevouringTendrilsReplacementEffect(DevouringTendrilsReplacementEffect effect) {
        super(effect);
        this.objectRef = effect.objectRef;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            controller.gainLife(2, game, source);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent() && objectRef.equals(new MageObjectReference(zce.getTarget(), game));
    }

    @Override
    public DevouringTendrilsReplacementEffect copy() {
        return new DevouringTendrilsReplacementEffect(this);
    }
}