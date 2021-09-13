package mage.cards.s;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public final class StalkingVampire extends CardImpl {

    public StalkingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.VAMPIRE);
        this.color.setBlack(true);

        this.nightCard = true;
        this.transformable = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Stalking Vampire.
        this.addAbility(new StalkingVampireBeginningOfUpkeepTriggeredAbility());
    }

    private StalkingVampire(final StalkingVampire card) {
        super(card);
    }

    @Override
    public StalkingVampire copy() {
        return new StalkingVampire(this);
    }
}

class StalkingVampireBeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    public StalkingVampireBeginningOfUpkeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new StalkingVampireTransformSourceEffect(), true);
    }

    public StalkingVampireBeginningOfUpkeepTriggeredAbility(final StalkingVampireBeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StalkingVampireBeginningOfUpkeepTriggeredAbility copy() {
        return new StalkingVampireBeginningOfUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform {this}.";
    }
}

class StalkingVampireTransformSourceEffect extends OneShotEffect {

    public StalkingVampireTransformSourceEffect() {
        super(Outcome.Transform);
        staticText = "transform {this}";
    }

    public StalkingVampireTransformSourceEffect(final StalkingVampireTransformSourceEffect effect) {
        super(effect);
    }

    @Override
    public StalkingVampireTransformSourceEffect copy() {
        return new StalkingVampireTransformSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Cost cost = new ManaCostsImpl("{2}{B}{B}");
            if (cost.pay(source, game, source, permanent.getControllerId(), false, null)) {
                new TransformSourceEffect(false).apply(game, source);
            }
            return true;
        }
        return false;
    }

}
