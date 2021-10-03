package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public final class ScreechingBat extends CardImpl {

    public ScreechingBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.BAT);

        this.transformable = true;
        this.secondSideCardClazz = StalkingVampire.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Screeching Bat.
        this.addAbility(new TransformAbility());
        this.addAbility(new ScreechingBatBeginningOfUpkeepTriggeredAbility());
    }

    private ScreechingBat(final ScreechingBat card) {
        super(card);
    }

    @Override
    public ScreechingBat copy() {
        return new ScreechingBat(this);
    }
}

class ScreechingBatBeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    public ScreechingBatBeginningOfUpkeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScreechingBatTransformSourceEffect(), true);
    }

    public ScreechingBatBeginningOfUpkeepTriggeredAbility(final ScreechingBatBeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScreechingBatBeginningOfUpkeepTriggeredAbility copy() {
        return new ScreechingBatBeginningOfUpkeepTriggeredAbility(this);
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

class ScreechingBatTransformSourceEffect extends OneShotEffect {

    public ScreechingBatTransformSourceEffect() {
        super(Outcome.Transform);
        staticText = "transform {this}";
    }

    public ScreechingBatTransformSourceEffect(final ScreechingBatTransformSourceEffect effect) {
        super(effect);
    }

    @Override
    public ScreechingBatTransformSourceEffect copy() {
        return new ScreechingBatTransformSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Cost cost = new ManaCostsImpl("{2}{B}{B}");
            if (cost.pay(source, game, source, permanent.getControllerId(), false, null)) {
                new TransformSourceEffect(true).apply(game, source);
            }
            return true;
        }
        return false;
    }

}
