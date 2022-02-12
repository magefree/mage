package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FinestHour extends CardImpl {

    public FinestHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}{U}");

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that
        // creature. After this phase, there is an additional combat phase.
        this.addAbility(new FinestHourAbility());
    }

    private FinestHour(final FinestHour card) {
        super(card);
    }

    @Override
    public FinestHour copy() {
        return new FinestHour(this);
    }

}

class FinestHourAbility extends TriggeredAbilityImpl {

    public FinestHourAbility() {
        super(Zone.BATTLEFIELD, new FinestHourEffect());
    }

    public FinestHourAbility(final FinestHourAbility ability) {
        super(ability);
    }

    @Override
    public FinestHourAbility copy() {
        return new FinestHourAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackers().get(0), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getTurn().getPhase(TurnPhase.COMBAT).getCount() == 0;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that creature. After this phase, there is an additional combat phase.";
    }

}

class FinestHourEffect extends OneShotEffect {

    public FinestHourEffect() {
        super(Outcome.Benefit);
    }

    public FinestHourEffect(final FinestHourEffect effect) {
        super(effect);
    }

    @Override
    public FinestHourEffect copy() {
        return new FinestHourEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.untap(game);
            game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
            return true;
        }
        return false;
    }

}
