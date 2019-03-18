
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RingsOfBrighthearth extends CardImpl {

    public RingsOfBrighthearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new RingsOfBrighthearthTriggeredAbility());
    }

    public RingsOfBrighthearth(final RingsOfBrighthearth card) {
        super(card);
    }

    @Override
    public RingsOfBrighthearth copy() {
        return new RingsOfBrighthearth(this);
    }
}

class RingsOfBrighthearthTriggeredAbility extends TriggeredAbilityImpl {

    RingsOfBrighthearthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RingsOfBrighthearthEffect(), false);
    }

    RingsOfBrighthearthTriggeredAbility(final RingsOfBrighthearthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RingsOfBrighthearthTriggeredAbility copy() {
        return new RingsOfBrighthearthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility != null && !(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                Effect effect = this.getEffects().get(0);
                effect.setValue("stackAbility", stackAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.";
    }
}

class RingsOfBrighthearthEffect extends OneShotEffect {

    RingsOfBrighthearthEffect() {
        super(Outcome.Benefit);
        this.staticText = ", you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.";
    }

    RingsOfBrighthearthEffect(final RingsOfBrighthearthEffect effect) {
        super(effect);
    }

    @Override
    public RingsOfBrighthearthEffect copy() {
        return new RingsOfBrighthearthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCostsImpl cost = new ManaCostsImpl("{2}");
        if (player != null) {
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? If you do, copy that ability. You may choose new targets for the copy.", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    StackAbility ability = (StackAbility) getValue("stackAbility");
                    Player controller = game.getPlayer(source.getControllerId());
                    Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                    if (ability != null && controller != null && sourcePermanent != null) {
                        ability.createCopyOnStack(game, source, source.getControllerId(), true);
                        game.informPlayers(sourcePermanent.getIdName() + ": " + controller.getLogName() + " copied activated ability");
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
