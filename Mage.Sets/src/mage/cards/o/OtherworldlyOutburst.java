
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OtherworldlyOutburst extends CardImpl {

    public OtherworldlyOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target creature gets +1/+0 until end of turn. When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new OtherworldlyOutburstEffect());
    }

    private OtherworldlyOutburst(final OtherworldlyOutburst card) {
        super(card);
    }

    @Override
    public OtherworldlyOutburst copy() {
        return new OtherworldlyOutburst(this);
    }
}

class OtherworldlyOutburstEffect extends OneShotEffect {

    public OtherworldlyOutburstEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token";
    }

    public OtherworldlyOutburstEffect(final OtherworldlyOutburstEffect effect) {
        super(effect);
    }

    @Override
    public OtherworldlyOutburstEffect copy() {
        return new OtherworldlyOutburstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new OtherworldlyOutburstDelayedTriggeredAbility(source.getFirstTarget()), source);
        return true;
    }
}

class OtherworldlyOutburstDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID target;

    public OtherworldlyOutburstDelayedTriggeredAbility(UUID target) {
        super(new CreateTokenEffect(new EldraziHorrorToken()), Duration.EndOfTurn);
        this.target = target;
    }

    public OtherworldlyOutburstDelayedTriggeredAbility(OtherworldlyOutburstDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public OtherworldlyOutburstDelayedTriggeredAbility copy() {
        return new OtherworldlyOutburstDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token.";
    }
}
