package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CompletedDungeonTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DragonToken2;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LootDispute extends CardImpl {

    public LootDispute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // When Loot Dispute enters the battlefield, you take the initiative and create a Treasure token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect());
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).setText("and create a Treasure token"));
        this.addAbility(ability);

        // Whenever you attack a player who has the initiative, create a Treasure token.
        this.addAbility(new LootDisputeTriggeredAbility());

        // Loud Ruckus — Whenever you complete a dungeon, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new CompletedDungeonTriggeredAbility(
                new CreateTokenEffect(new DragonToken2())
        ).withFlavorWord("Loud Ruckus"));
    }

    private LootDispute(final LootDispute card) {
        super(card);
    }

    @Override
    public LootDispute copy() {
        return new LootDispute(this);
    }
}

class LootDisputeTriggeredAbility extends TriggeredAbilityImpl {

    LootDisputeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever you attack a player who has the initiative, ");
    }

    private LootDisputeTriggeredAbility(final LootDisputeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LootDisputeTriggeredAbility copy() {
        return new LootDisputeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && event.getTargetId().equals(game.getInitiativeId());
    }
}
