package mage.cards.o;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Oubliette extends CardImpl {

    public Oubliette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // When Oubliette enters the battlefield, target creature phases out until Oubliette leaves the battlefield. Tap that creature as it phases in this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new OubliettePhaseOutEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Oubliette(final Oubliette card) {
        super(card);
    }

    @Override
    public Oubliette copy() {
        return new Oubliette(this);
    }
}

class OubliettePhaseOutEffect extends OneShotEffect {

    OubliettePhaseOutEffect() {
        super(Outcome.Detriment);
        staticText = "target creature phases out until {this} leaves the battlefield. " +
                "Tap that creature as it phases in this way.";
    }

    private OubliettePhaseOutEffect(final OubliettePhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public OubliettePhaseOutEffect copy() {
        return new OubliettePhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || permanent == null) {
            return false;
        }

        MageObjectReference mor = new MageObjectReference(permanent, game);
        permanent.phaseOut(game);
        game.addEffect(new OubliettePhasePreventEffect(mor), source);
        game.addDelayedTriggeredAbility(new OublietteDelayedTriggeredAbility(mor), source);

        return true;
    }
}

class OubliettePhasePreventEffect extends ContinuousRuleModifyingEffectImpl {

    private final MageObjectReference mor;

    OubliettePhasePreventEffect(MageObjectReference mor) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.mor = mor;
    }

    private OubliettePhasePreventEffect(final OubliettePhasePreventEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public OubliettePhasePreventEffect copy() {
        return new OubliettePhasePreventEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourcePermanentIfItStillExists(game) != null
                && this.mor.refersTo(event.getTargetId(), game);
    }
}

class OublietteDelayedTriggeredAbility extends DelayedTriggeredAbility {

    OublietteDelayedTriggeredAbility(MageObjectReference mor) {
        super(new OubliettePhaseInEffect(mor), Duration.Custom, true, false);
        this.usesStack = false;
    }

    private OublietteDelayedTriggeredAbility(final OublietteDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OublietteDelayedTriggeredAbility copy() {
        return new OublietteDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        return ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
    }
}

class OubliettePhaseInEffect extends OneShotEffect {

    private final MageObjectReference mor;

    OubliettePhaseInEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private OubliettePhaseInEffect(final OubliettePhaseInEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public OubliettePhaseInEffect copy() {
        return new OubliettePhaseInEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = mor.getPermanent(game);
        if (permanent == null) {
            return false;
        }

        permanent.setTapped(true); // Used instead of .tap so that tapped triggered abilities don't trigger

        return permanent.phaseIn(game);
    }
}
