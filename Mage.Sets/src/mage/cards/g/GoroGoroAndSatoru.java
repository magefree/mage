package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonSpiritToken;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoroGoroAndSatoru extends CardImpl {

    public GoroGoroAndSatoru(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever one or more creatures you control that entered the battlefield this turn deal combat damage to a
        // player, create a 5/5 red Dragon Spirit creature token with flying.
        this.addAbility(new GoroGoroAndSatoruTriggeredAbility());

        // {1}{R}: Creatures you control gain haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), new ManaCostsImpl<>("{1}{R}")));
    }

    private GoroGoroAndSatoru(final GoroGoroAndSatoru card) {
        super(card);
    }

    @Override
    public GoroGoroAndSatoru copy() {
        return new GoroGoroAndSatoru(this);
    }
}

class GoroGoroAndSatoruTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creatures you control that entered the battlefield this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    private final Set<UUID> damagedPlayerIds = new HashSet<>();

    public GoroGoroAndSatoruTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new DragonSpiritToken()), false);
    }

    public GoroGoroAndSatoruTriggeredAbility(final GoroGoroAndSatoruTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoroGoroAndSatoruTriggeredAbility copy() {
        return new GoroGoroAndSatoruTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.isControlledBy(this.getControllerId()) &&
                    filter.match(p, getControllerId(), this, game) &&
                    !damagedPlayerIds.contains(event.getPlayerId())) {
                damagedPlayerIds.add(event.getPlayerId());
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY ||
                (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId()))) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control that entered the battlefield this turn deal combat damage to a player, create a 5/5 red Dragon Spirit creature token with flying.";
    }
}