
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.HazezonTamarSandWarriorToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HazezonTamar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sand Warriors");

    static {
        filter.add(SubType.SAND.getPredicate());
        filter.add(SubType.WARRIOR.getPredicate());
    }

    public HazezonTamar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Hazezon Tamar enters the battlefield, create X 1/1 Sand Warrior creature tokens that are red, green, and white at the beginning of your next upkeep, where X is the number of lands you control at that time.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HazezonTamarEntersEffect(), false));
        // When Hazezon leaves the battlefield, exile all Sand Warriors.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filter), false));
    }

    private HazezonTamar(final HazezonTamar card) {
        super(card);
    }

    @Override
    public HazezonTamar copy() {
        return new HazezonTamar(this);
    }
}

class HazezonTamarEntersEffect extends OneShotEffect {

    public HazezonTamarEntersEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 1/1 Sand Warrior creature tokens that are red, green, and white at the beginning of your next upkeep, where X is the number of lands you control at that time";
    }

    public HazezonTamarEntersEffect(final HazezonTamarEntersEffect effect) {
        super(effect);
    }

    @Override
    public HazezonTamarEntersEffect copy() {
        return new HazezonTamarEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Effect effect = new CreateTokenEffect(new HazezonTamarSandWarriorToken(), new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent()));
            effect.setText("create X 1/1 Sand Warrior creature tokens that are red, green, and white, where X is the number of lands you control at that time");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
