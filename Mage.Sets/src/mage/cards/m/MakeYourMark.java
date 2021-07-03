package mage.cards.m;

import mage.MageObjectReference;
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
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spirit32Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeYourMark extends CardImpl {

    public MakeYourMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R/W}");

        // Target creature gets +1/+0 until end of turn. When that creature dies this turn, create a 3/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new MakeYourMarkEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MakeYourMark(final MakeYourMark card) {
        super(card);
    }

    @Override
    public MakeYourMark copy() {
        return new MakeYourMark(this);
    }
}

class MakeYourMarkEffect extends OneShotEffect {

    MakeYourMarkEffect() {
        super(Outcome.Benefit);
        staticText = "When that creature dies this turn, create a 3/2 red and white Spirit creature token.";
    }

    private MakeYourMarkEffect(final MakeYourMarkEffect effect) {
        super(effect);
    }

    @Override
    public MakeYourMarkEffect copy() {
        return new MakeYourMarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new MakeYourMarkDelayedTriggeredAbility(permanent, game), source);
        return true;
    }
}

class MakeYourMarkDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    MakeYourMarkDelayedTriggeredAbility(Permanent permanent, Game game) {
        super(new CreateTokenEffect(new Spirit32Token()), Duration.EndOfTurn, false, false);
        this.mor = new MageObjectReference(permanent, game);
    }

    private MakeYourMarkDelayedTriggeredAbility(final MakeYourMarkDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return mor.refersTo(((ZoneChangeEvent) event).getTarget(), game);
    }

    @Override
    public MakeYourMarkDelayedTriggeredAbility copy() {
        return new MakeYourMarkDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, create a 3/2 red and white Spirit creature token.";
    }
}