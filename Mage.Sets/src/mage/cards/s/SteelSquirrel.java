
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
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

    private SteelSquirrel(final SteelSquirrel card) {
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
        setTriggerPhrase("Whenever you roll a 5 or higher on a die, ");
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
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        // silver border card must look for "result" instead "natural result"
        if (this.isControlledBy(event.getPlayerId()) && drEvent.getResult() < 5) {
            return false;
        }

        this.getEffects().setValue("rolled", drEvent.getResult());
        return true;
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
        Integer amount = (Integer) this.getValue("rolled");
        if (controller != null && permanent != null && amount != null) {
            game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
