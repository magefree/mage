package mage.cards.n;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NefariousImp extends CardImpl {

    public NefariousImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more permanents you control leave the battlefield, scry 1.
        this.addAbility(new NefariousImpTriggeredAbility());
    }

    private NefariousImp(final NefariousImp card) {
        super(card);
    }

    @Override
    public NefariousImp copy() {
        return new NefariousImp(this);
    }
}

class NefariousImpTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    NefariousImpTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(1));
        setTriggerPhrase("Whenever one or more permanents you control leave the battlefield, ");
    }

    private NefariousImpTriggeredAbility(final NefariousImpTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NefariousImpTriggeredAbility copy() {
        return new NefariousImpTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getFromZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }
}
