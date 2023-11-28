package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FaerieRogueToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class AlelaCunningConqueror extends CardImpl {

    public AlelaCunningConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast your first spell during each opponent's turn, create a 1/1 black Faerie Rogue creature token with flying.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new CreateTokenEffect(new FaerieRogueToken()), false
        ));

        // Whenever one or more Faeries you control deal combat damage to a player, goad target creature that player controls.
        TriggeredAbility trigger = new AlelaCunningConquerorTriggeredAbility();
        this.addAbility(trigger);
    }

    private AlelaCunningConqueror(final AlelaCunningConqueror card) {
        super(card);
    }

    @Override
    public AlelaCunningConqueror copy() {
        return new AlelaCunningConqueror(this);
    }
}


class AlelaCunningConquerorTriggeredAbility extends TriggeredAbilityImpl {

    AlelaCunningConquerorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoadTargetEffect(), false);
    }

    private AlelaCunningConquerorTriggeredAbility(final AlelaCunningConquerorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AlelaCunningConquerorTriggeredAbility copy() {
        return new AlelaCunningConquerorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchEvent dEvent = (DamagedBatchEvent) event;
        if (!dEvent.isCombatDamage()) {
            return false;
        }
        List<DamagedEvent> events = dEvent
                .getEvents()
                .stream()
                .filter(e -> {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(e.getSourceId());
                    return permanent != null
                            && permanent.hasSubtype(SubType.FAERIE, game)
                            && permanent.isControlledBy(this.getControllerId());
                })
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            return false;
        }

        Player opponent = game.getPlayer(dEvent.getPlayerId());
        if (opponent == null) {
            return false;
        }

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetCreaturePermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Faeries you control " +
                "deal combat damage to a player, goad target creature that player controls.";
    }
}
