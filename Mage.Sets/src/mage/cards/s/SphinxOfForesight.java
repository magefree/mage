package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfForesight extends CardImpl {

    public SphinxOfForesight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may reveal this card from your opening hand. If you do, scry 3 at the beginning of your first upkeep.
        Ability ability = new ChancellorAbility(
                new SphinxOfForesightDelayedTriggeredAbility(),
                "scry 3 at the beginning of your first upkeep."
        );
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new ScryEffect(1, false),
                TargetController.YOU, false
        ));
    }

    private SphinxOfForesight(final SphinxOfForesight card) {
        super(card);
    }

    @Override
    public SphinxOfForesight copy() {
        return new SphinxOfForesight(this);
    }
}

class SphinxOfForesightDelayedTriggeredAbility extends DelayedTriggeredAbility {

    SphinxOfForesightDelayedTriggeredAbility() {
        super(new ScryEffect(3));
    }

    private SphinxOfForesightDelayedTriggeredAbility(SphinxOfForesightDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(this.controllerId);
    }

    @Override
    public SphinxOfForesightDelayedTriggeredAbility copy() {
        return new SphinxOfForesightDelayedTriggeredAbility(this);
    }
}
