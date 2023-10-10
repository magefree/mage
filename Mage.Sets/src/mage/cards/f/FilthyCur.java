
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author cbt33
 */
public final class FilthyCur extends CardImpl {

    public FilthyCur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Filthy Cur is dealt damage, you lose that much life.
        this.addAbility(new DealtDamageLoseLifeTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(0), false));

    }

    private FilthyCur(final FilthyCur card) {
        super(card);
    }

    @Override
    public FilthyCur copy() {
        return new FilthyCur(this);
    }
}

class DealtDamageLoseLifeTriggeredAbility extends TriggeredAbilityImpl {

    public DealtDamageLoseLifeTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    private DealtDamageLoseLifeTriggeredAbility(final DealtDamageLoseLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtDamageLoseLifeTriggeredAbility copy() {
        return new DealtDamageLoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            this.getEffects().clear();
            this.addEffect(new LoseLifeSourceControllerEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, you lose that much life.";
    }
}
