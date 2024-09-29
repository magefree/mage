
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class LairwatchGiant extends CardImpl {

    public LairwatchGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Lairwatch Giant can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(1)));

        // Whenever Lairwatch Giant blocks two or more creatures, it gains first strike until end of turn.
        this.addAbility(new LairwatchGiantTriggeredAbility());

    }

    private LairwatchGiant(final LairwatchGiant card) {
        super(card);
    }

    @Override
    public LairwatchGiant copy() {
        return new LairwatchGiant(this);
    }
}

class LairwatchGiantTriggeredAbility extends TriggeredAbilityImpl {

    LairwatchGiantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()));
    }

    private LairwatchGiantTriggeredAbility(final LairwatchGiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LairwatchGiantTriggeredAbility copy() {
        return new LairwatchGiantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent((this.getSourceId()));
        if (permanent != null) {
            return permanent.getBlocking() > 1;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Lairwatch Giant blocks two or more creatures, it gains first strike until end of turn.";
    }
}
