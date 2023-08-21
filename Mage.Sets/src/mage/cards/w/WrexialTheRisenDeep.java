package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
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
 * @author jeffwadsworth, xenohedron
 */
public final class WrexialTheRisenDeep extends CardImpl {

    public WrexialTheRisenDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Whenever Wrexial, the Risen Deep deals combat damage to a player, you may cast target instant or sorcery card from that player's graveyard without paying its mana cost.
        // If that card would be put into a graveyard this turn, exile it instead.
        this.addAbility(new WrexialTheRisenDeepTriggeredAbility());
    }

    private WrexialTheRisenDeep(final WrexialTheRisenDeep card) {
        super(card);
    }

    @Override
    public WrexialTheRisenDeep copy() {
        return new WrexialTheRisenDeep(this);
    }
}

class WrexialTheRisenDeepTriggeredAbility extends TriggeredAbilityImpl {

    WrexialTheRisenDeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MayCastTargetThenExileEffect(true)
                .setText("you may cast target instant or sorcery card from "
                        + "that player's graveyard without paying its mana cost. "
                        + ThatSpellGraveyardExileReplacementEffect.RULE_A), false);
        setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    private WrexialTheRisenDeepTriggeredAbility(final WrexialTheRisenDeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WrexialTheRisenDeepTriggeredAbility copy() {
        return new WrexialTheRisenDeepTriggeredAbility(this);
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
