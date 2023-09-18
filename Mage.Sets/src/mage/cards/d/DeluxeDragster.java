package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801, Grath, xenohedron
 */
public final class DeluxeDragster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Vehicles");

    static {
        filter.add(Predicates.not(SubType.VEHICLE.getPredicate()));
    }

    public DeluxeDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Deluxe Dragster can’t be blocked except by Vehicles.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Deluxe Dragster deals combat damage to a player, you may cast target instant or sorcery card from
        // that player’s graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.addAbility(new DeluxeDragsterTriggeredAbility());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DeluxeDragster(final DeluxeDragster card) {
        super(card);
    }

    @Override
    public DeluxeDragster copy() {
        return new DeluxeDragster(this);
    }
}

class DeluxeDragsterTriggeredAbility extends TriggeredAbilityImpl {

    DeluxeDragsterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MayCastTargetThenExileEffect(true)
                .setText("you may cast target instant or sorcery card from "
                        + "that player's graveyard without paying its mana cost. "
                        + ThatSpellGraveyardExileReplacementEffect.RULE_A), false);
        setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    private DeluxeDragsterTriggeredAbility(final DeluxeDragsterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeluxeDragsterTriggeredAbility copy() {
        return new DeluxeDragsterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId) || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        if (damagedPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("instant or sorcery card from that player's graveyard");
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        Target target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }
}
