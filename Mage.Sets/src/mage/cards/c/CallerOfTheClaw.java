
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.BearToken;
import mage.watchers.Watcher;

/**
 *
 * @author Plopman
 */
public final class CallerOfTheClaw extends CardImpl {

    public CallerOfTheClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Caller of the Claw enters the battlefield, create a 2/2 green Bear creature token for each nontoken creature put into your graveyard from the battlefield this turn.
        this.getSpellAbility().addWatcher(new CallerOfTheClawWatcher());
        Effect effect = new CreateTokenEffect(new BearToken(), new CallerOfTheClawDynamicValue());
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private CallerOfTheClaw(final CallerOfTheClaw card) {
        super(card);
    }

    @Override
    public CallerOfTheClaw copy() {
        return new CallerOfTheClaw(this);
    }
}

class CallerOfTheClawWatcher extends Watcher {

    private int creaturesCount = 0;

    public CallerOfTheClawWatcher() {
        super(WatcherScope.PLAYER);
        condition = true;
    }

    public int getCreaturesCount() {
        return creaturesCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent card = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.isOwnedBy(this.controllerId) && card.isCreature(game) && !(card instanceof PermanentToken)) {
                creaturesCount++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesCount = 0;
    }
}

class CallerOfTheClawDynamicValue implements DynamicValue {

    @Override
    public CallerOfTheClawDynamicValue copy() {
        return new CallerOfTheClawDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "nontoken creature put into your graveyard from the battlefield this turn";
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CallerOfTheClawWatcher watcher = game.getState().getWatcher(CallerOfTheClawWatcher.class, sourceAbility.getControllerId());
        if (watcher != null) {
            return watcher.getCreaturesCount();
        }
        return 0;
    }
}
