
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class SteelSquirrel extends CardImpl {

    public SteelSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you roll a 5 or higher on a die, Steel Squirrel gets +X/+X until end of turn, where X is the result.
        this.addAbility(new SteelSquirrelTriggeredAbility());

        // 6: Roll a six-sided die.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RollDiceEffect(null, 6), new GenericManaCost(6));
        this.addAbility(ability);
    }

    public SteelSquirrel(final SteelSquirrel card) {
        super(card);
    }

    @Override
    public SteelSquirrel copy() {
        return new SteelSquirrel(this);
    }
}

class SteelSquirrelTriggeredAbility extends TriggeredAbilityImpl {

    public SteelSquirrelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SteelSquirrelEffect());
    }

    public SteelSquirrelTriggeredAbility(final SteelSquirrelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SteelSquirrelTriggeredAbility copy() {
        return new SteelSquirrelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId()) && event.getFlag()) {
            if (event.getAmount() >= 5) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("rolled", event.getAmount());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 5 or higher on a die, " + super.getRule();
    }
}

class SteelSquirrelEffect extends OneShotEffect {

    public SteelSquirrelEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} gets +X/+X until end of turn, where X is the result";
    }

    public SteelSquirrelEffect(final SteelSquirrelEffect effect) {
        super(effect);
    }

    @Override
    public SteelSquirrelEffect copy() {
        return new SteelSquirrelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (this.getValue("rolled") != null) {
                int rolled = (Integer) this.getValue("rolled");
                game.addEffect(new BoostSourceEffect(rolled, rolled, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }
}
