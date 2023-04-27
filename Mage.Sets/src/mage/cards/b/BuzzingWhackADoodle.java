
package mage.cards.b;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author spjspj
 */
public final class BuzzingWhackADoodle extends CardImpl {

    public BuzzingWhackADoodle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Buzzing Whack-a-Doodle enters the battlefield, you and an opponent each secretly choose Whack or Doodle. Then those choices are revealed. If the choices match, Buzzing Whack-a-Doodle has that ability. Otherwise it has Buzz.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BuzzingWhackADoodleEffect(), false));

        // *Whack - T: Target player loses 2 life.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new TapSourceCost(), new WhackCondition());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // *Doodle - T: You gain 3 life.
        Ability ability2 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new TapSourceCost(), new DoodleCondition());
        this.addAbility(ability2);

        // *Buzz - 2, T: Draw a card.
        Ability ability3 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}"), new BuzzCondition());
        ability3.addCost(new TapSourceCost());
        this.addAbility(ability3);
    }

    private BuzzingWhackADoodle(final BuzzingWhackADoodle card) {
        super(card);
    }

    @Override
    public BuzzingWhackADoodle copy() {
        return new BuzzingWhackADoodle(this);
    }
}

class BuzzingWhackADoodleEffect extends OneShotEffect {

    BuzzingWhackADoodleEffect() {
        super(Outcome.Benefit);
        this.staticText = "You and an opponent each secretly choose Whack or Doodle. Then those choices are revealed. If the choices match, {this} has that ability. Otherwise it has Buzz";
    }

    BuzzingWhackADoodleEffect(final BuzzingWhackADoodleEffect effect) {
        super(effect);
    }

    @Override
    public BuzzingWhackADoodleEffect copy() {
        return new BuzzingWhackADoodleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int whackCount = 0;
            int doodleCount = 0;

            if (controller.chooseUse(Outcome.Benefit, "Choose Whack (yes) or Doodle (no)?", source, game)) {
                whackCount++;
            } else {
                doodleCount++;
            }

            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            if (!opponents.isEmpty()) {
                Player opponent = game.getPlayer(opponents.iterator().next());
                if (opponents.size() > 1) {
                    Target targetOpponent = new TargetOpponent(true);
                    if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                        game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to choose Whack or Doodle");
                    }
                }

                if (opponent != null) {
                    if (opponent.chooseUse(Outcome.Benefit, "Choose Whack (yes) or Doodle (no)?", source, game)) {
                        whackCount++;
                    } else {
                        doodleCount++;
                    }
                }
            }

            if (whackCount == 2) {
                game.informPlayers("Whack was chosen");
                game.getState().setValue("whack" + source.getSourceId(), Boolean.TRUE);
            } else if (doodleCount == 2) {
                game.informPlayers("Doodle was chosen");
                game.getState().setValue("doodle" + source.getSourceId(), Boolean.TRUE);
            } else {
                game.informPlayers("Buzz was chosen");
                game.getState().setValue("buzz" + source.getSourceId(), Boolean.TRUE);
            }
            return true;
        }
        return false;
    }
}

class WhackCondition extends IntCompareCondition {

    WhackCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("whack" + source.getSourceId());
        if (object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked 'Whack'";
    }
}

class DoodleCondition extends IntCompareCondition {

    DoodleCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("doodle" + source.getSourceId());
        if (object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked 'Doodle'";
    }
}

class BuzzCondition extends IntCompareCondition {

    BuzzCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("buzz" + source.getSourceId());
        if (object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked differently";
    }
}
