package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrutalCathar extends CardImpl {

    public BrutalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.m.MoonrageBrute.class;

        // When this creature enters the battlefield or transforms into Brutal Cathar, exile target creature an opponent controls until this creature leaves the battlefield.
        this.addAbility(new BrutalCatharTriggeredAbility());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private BrutalCathar(final BrutalCathar card) {
        super(card);
    }

    @Override
    public BrutalCathar copy() {
        return new BrutalCathar(this);
    }
}

class BrutalCatharTriggeredAbility extends TriggeredAbilityImpl {

    public BrutalCatharTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileUntilSourceLeavesEffect("creature an opponent controls"), false);
        this.addTarget(new TargetOpponentsCreaturePermanent());
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
    }

    public BrutalCatharTriggeredAbility(final BrutalCatharTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BrutalCatharTriggeredAbility copy() {
        return new BrutalCatharTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        switch (event.getType()) {
            case TRANSFORMED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                return permanent != null && !permanent.isTransformed();
            case ENTERS_THE_BATTLEFIELD:
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When this creature enters the battlefield or transforms into {this}, " +
                "exile target creature an opponent controls until this creature leaves the battlefield.";
    }
}
