package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class ScionOfDarkness extends CardImpl {

    public ScionOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Scion of Darkness deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        this.addAbility(new ScionOfDarknessTriggeredAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private ScionOfDarkness(final ScionOfDarkness card) {
        super(card);
    }

    @Override
    public ScionOfDarkness copy() {
        return new ScionOfDarkness(this);
    }
}

class ScionOfDarknessTriggeredAbility extends TriggeredAbilityImpl {

    public ScionOfDarknessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
    }

    public ScionOfDarknessTriggeredAbility(final ScionOfDarknessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScionOfDarknessTriggeredAbility copy() {
        return new ScionOfDarknessTriggeredAbility(this);
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
        FilterCard filter = new FilterCard("creature in " + damagedPlayer.getName() + "'s graveyard");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "you may put target creature card from that player's "
                + "graveyard onto the battlefield under your control.";
    }
}
