
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class FleetingMemories extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Clue");

    static {
        filter.add(SubType.CLUE.getPredicate());
    }

    public FleetingMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // When Fleeting Memories enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(), false));

        // Whenever you sacrifice a Clue, target player puts the top three cards of their graveyard into their graveyard.
        Ability ability = new FleetingMemoriesTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FleetingMemories(final FleetingMemories card) {
        super(card);
    }

    @Override
    public FleetingMemories copy() {
        return new FleetingMemories(this);
    }
}

class FleetingMemoriesTriggeredAbility extends TriggeredAbilityImpl {

    public FleetingMemoriesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(3));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a Clue, ");
    }

    public FleetingMemoriesTriggeredAbility(final FleetingMemoriesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FleetingMemoriesTriggeredAbility copy() {
        return new FleetingMemoriesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.CLUE, game);
    }
}