
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class AbolisherOfBloodlines extends CardImpl {

    public AbolisherOfBloodlines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Abolisher of Bloodlines, target opponent sacrifices three creatures.
        this.addAbility(new AbolisherOfBloodlinesAbility());
    }

    public AbolisherOfBloodlines(final AbolisherOfBloodlines card) {
        super(card);
    }

    @Override
    public AbolisherOfBloodlines copy() {
        return new AbolisherOfBloodlines(this);
    }
}

class AbolisherOfBloodlinesAbility extends TriggeredAbilityImpl {

    static final String RULE_TEXT = "When this creature transforms into Abolisher of Bloodlines, target opponent sacrifices three creatures";

    public AbolisherOfBloodlinesAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 3, "Target opponent"), false);
        Target target = new TargetOpponent();
        this.addTarget(target);
    }

    public AbolisherOfBloodlinesAbility(final AbolisherOfBloodlinesAbility ability) {
        super(ability);
    }

    @Override
    public AbolisherOfBloodlinesAbility copy() {
        return new AbolisherOfBloodlinesAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return RULE_TEXT + '.';
    }
}
