package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SchemaThief extends CardImpl {

    public SchemaThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Schema Thief deals combat damage to a player, create a token that's a copy of target artifact that player controls.
        this.addAbility(new SchemaThiefTriggeredAbility());
    }

    private SchemaThief(final SchemaThief card) {
        super(card);
    }

    @Override
    public SchemaThief copy() {
        return new SchemaThief(this);
    }
}

class SchemaThiefTriggeredAbility extends TriggeredAbilityImpl {

    SchemaThiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenCopyTargetEffect(), false);
    }

    private SchemaThiefTriggeredAbility(final SchemaThiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SchemaThiefTriggeredAbility copy() {
        return new SchemaThiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(this.getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactPermanent("artifact " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, create a token " +
                "that's a copy of target artifact that player controls.";
    }
}
