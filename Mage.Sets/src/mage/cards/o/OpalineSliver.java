
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 *
 * @author anonymous
 */
public final class OpalineSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("All Slivers");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public OpalineSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "Whenever this permanent becomes the target of a spell an opponent controls, you may draw a card."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new OpalineSliverTriggeredAbility(), Duration.WhileOnBattlefield,
                filter, "All Slivers have \"Whenever this permanent becomes the target of a spell an opponent controls, you may draw a card.\"")));
    }

    private OpalineSliver(final OpalineSliver card) {
        super(card);
    }

    @Override
    public OpalineSliver copy() {
        return new OpalineSliver(this);
    }
}

class OpalineSliverTriggeredAbility extends TriggeredAbilityImpl {

    public OpalineSliverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public OpalineSliverTriggeredAbility(final OpalineSliverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OpalineSliverTriggeredAbility copy() {
        return new OpalineSliverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject spell = game.getStack().getStackObject(event.getSourceId());

        if (spell == null) {
            return false;
        } else {
            return event.getTargetId().equals(this.getSourceId())
                    && game.getOpponents(this.controllerId).contains(event.getPlayerId())
                    && StaticFilters.FILTER_SPELL_A.match(spell, getControllerId(), this, game);
        }
    }

    @Override
    public String getRule() {
        return "Whenever this permanent becomes the target of a spell an opponent controls, you may draw a card.";
    }

}
