
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class GroundPounder extends CardImpl {

    public GroundPounder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // 3G: Roll a six-sided die. Ground Pounder gets +X/+X until end of turn, where X is the result.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GroundPounderEffect(), new ManaCostsImpl("{3}{G}")));

        // Whenever you roll a 5 or higher on a die, Ground Pounder gains trample until end of turn.
        this.addAbility(new GroundPounderTriggeredAbility());
    }

    public GroundPounder(final GroundPounder card) {
        super(card);
    }

    @Override
    public GroundPounder copy() {
        return new GroundPounder(this);
    }
}

class GroundPounderEffect extends OneShotEffect {

    public GroundPounderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. {this} gets +X/+X until end of turn, where X is the result";
    }

    public GroundPounderEffect(final GroundPounderEffect effect) {
        super(effect);
    }

    @Override
    public GroundPounderEffect copy() {
        return new GroundPounderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}

class GroundPounderTriggeredAbility extends TriggeredAbilityImpl {

    public GroundPounderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), false);
    }

    public GroundPounderTriggeredAbility(final GroundPounderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GroundPounderTriggeredAbility copy() {
        return new GroundPounderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId()) && event.getFlag()) {
            if (event.getAmount() >= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 5 or higher on a die, {this} gains trample until end of turn";
    }
}
