package mage.cards.b;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public class BlessedDefiance extends CardImpl {

    public BlessedDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature you control gets +2/+0 and gains lifelink until end of turn. When that creature dies this turn, create a 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("Target creature you control gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains lifelink until end of turn"));
        this.getSpellAbility().addEffect(new BlessedDefianceEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

    }

    private BlessedDefiance(final BlessedDefiance card) {
        super(card);
    }

    @Override
    public BlessedDefiance copy() {
        return new BlessedDefiance(this);
    }
}

class BlessedDefianceEffect extends OneShotEffect {

    BlessedDefianceEffect() {
        super(Outcome.Benefit);
        staticText = "When that creature dies this turn, create a 1/1 white Spirit creature token with flying.";
    }

    private BlessedDefianceEffect(final BlessedDefianceEffect effect) {
        super(effect);
    }

    @Override
    public BlessedDefianceEffect copy() {
        return new BlessedDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new BlessedDefianceDelayedTriggeredAbility(permanent, game), source);
        return true;
    }
}

class BlessedDefianceDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    BlessedDefianceDelayedTriggeredAbility(Permanent permanent, Game game) {
        super(new CreateTokenEffect(new SpiritWhiteToken()), Duration.EndOfTurn, false, false);
        this.mor = new MageObjectReference(permanent, game);
    }

    private BlessedDefianceDelayedTriggeredAbility(final BlessedDefianceDelayedTriggeredAbility ability) {
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
    public BlessedDefianceDelayedTriggeredAbility copy() {
        return new BlessedDefianceDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, create a 1/1 white Spirit creature token with flying.";
    }
}
