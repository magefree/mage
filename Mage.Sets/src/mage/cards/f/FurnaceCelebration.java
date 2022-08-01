
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FurnaceCelebration extends CardImpl {

    public FurnaceCelebration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");

        this.addAbility(new FurnaceCelebrationAbility());
    }

    private FurnaceCelebration(final FurnaceCelebration card) {
        super(card);
    }

    @Override
    public FurnaceCelebration copy() {
        return new FurnaceCelebration(this);
    }

}

class FurnaceCelebrationAbility extends TriggeredAbilityImpl {

    public FurnaceCelebrationAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DamageTargetEffect(2), new ManaCostsImpl<>("{2}")));
        this.addTarget(new TargetAnyTarget());
    }

    public FurnaceCelebrationAbility(final FurnaceCelebrationAbility ability) {
        super(ability);
    }

    @Override
    public FurnaceCelebrationAbility copy() {
        return new FurnaceCelebrationAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId()) && !event.getTargetId().equals(sourceId);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you sacrifice another permanent, " ;
    }


}