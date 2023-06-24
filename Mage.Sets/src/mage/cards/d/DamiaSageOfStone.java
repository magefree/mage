
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DamiaSageOfStone extends CardImpl {

    public DamiaSageOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // At the beginning of your upkeep, if you have fewer than seven cards in hand, draw cards equal to the difference.
        this.addAbility(new DamiaSageOfStoneTriggeredAbility());
    }

    private DamiaSageOfStone(final DamiaSageOfStone card) {
        super(card);
    }

    @Override
    public DamiaSageOfStone copy() {
        return new DamiaSageOfStone(this);
    }
}

class DamiaSageOfStoneTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    DamiaSageOfStoneTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(new IntPlusDynamicValue(7, new MultipliedValue(CardsInControllerHandCount.instance, -1))), TargetController.YOU, false);
    }

    DamiaSageOfStoneTriggeredAbility(final DamiaSageOfStoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DamiaSageOfStoneTriggeredAbility copy() {
        return new DamiaSageOfStoneTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            return player.getHand().size() < 7;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you have fewer than seven cards in hand, draw cards equal to the difference";
    }
}
