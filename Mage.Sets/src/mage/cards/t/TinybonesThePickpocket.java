package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
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
 * @author Susucr
 */
public final class TinybonesThePickpocket extends CardImpl {

    public TinybonesThePickpocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Tinybones, the Pickpocket deals combat damage to a player, you may cast target nonland permanent card from that player's graveyard, and mana of any type can be spent to cast that spell.
        this.addAbility(new TinybonesThePickpocketTriggeredAbility());
    }

    private TinybonesThePickpocket(final TinybonesThePickpocket card) {
        super(card);
    }

    @Override
    public TinybonesThePickpocket copy() {
        return new TinybonesThePickpocket(this);
    }
}

/**
 * Similar to {@link mage.cards.w.WrexialTheRisenDeep}
 */
class TinybonesThePickpocketTriggeredAbility extends TriggeredAbilityImpl {

    TinybonesThePickpocketTriggeredAbility() {
        super(
                Zone.BATTLEFIELD,
                new MayCastTargetCardEffect(CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, false)
                        .setText("you may cast target nonland permanent card from "
                                + "that player's graveyard, and mana of any type can be spent to cast that spell"),
                false
        );
        setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    private TinybonesThePickpocketTriggeredAbility(final TinybonesThePickpocketTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TinybonesThePickpocketTriggeredAbility copy() {
        return new TinybonesThePickpocketTriggeredAbility(this);
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
        FilterCard filter = new FilterPermanentCard("nonland permanent card from " + damagedPlayer.getName() + "'s graveyard");
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        Target target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }
}
