package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenerableWarsinger extends CardImpl {

    public VenerableWarsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Venerable Warsinger deals combat damage to a player, you may return target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of damage that Venerable Warsinger dealt to that player.
        this.addAbility(new VenerableWarsingerTriggeredAbility());
    }

    private VenerableWarsinger(final VenerableWarsinger card) {
        super(card);
    }

    @Override
    public VenerableWarsinger copy() {
        return new VenerableWarsinger(this);
    }
}

class VenerableWarsingerTriggeredAbility extends TriggeredAbilityImpl {

    VenerableWarsingerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
    }

    private VenerableWarsingerTriggeredAbility(final VenerableWarsingerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VenerableWarsingerTriggeredAbility copy() {
        return new VenerableWarsingerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null
                || !event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard(
                "creature card with mana value " + event.getAmount() + " less from your graveyard"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, event.getAmount() + 1));
        this.getTargets().clear();
        this.addTarget(new TargetCardInYourGraveyard(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may return target creature card " +
                "with mana value X or less from your graveyard to the battlefield, " +
                "where X is the amount of damage {this} dealt to that player.";
    }
}
