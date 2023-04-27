package mage.cards.j;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class JujuBubble extends CardImpl {

    public JujuBubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // When you play a card, sacrifice Juju Bubble.
        this.addAbility(new JujuBubbleTriggeredAbility());

        // {2}: You gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(1), new GenericManaCost(1)));
    }

    private JujuBubble(final JujuBubble card) {
        super(card);
    }

    @Override
    public JujuBubble copy() {
        return new JujuBubble(this);
    }
}

class JujuBubbleTriggeredAbility extends TriggeredAbilityImpl {

    JujuBubbleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    JujuBubbleTriggeredAbility(final JujuBubbleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JujuBubbleTriggeredAbility copy() {
        return new JujuBubbleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "When you play a card, sacrifice {this}";
    }
}
