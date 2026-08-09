package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author muz
 */
public final class ThorinCompanysLeader extends CardImpl {

    public ThorinCompanysLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever a Dwarf you control deals combat damage to a player or battle, create two Treasure tokens.
        this.addAbility(new ThorinCompanysLeaderTriggeredAbility());

        // {10}: Creatures you control gain double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false
            ), new ManaCostsImpl<>("{10}")
        ));
    }

    private ThorinCompanysLeader(final ThorinCompanysLeader card) {
        super(card);
    }

    @Override
    public ThorinCompanysLeader copy() {
        return new ThorinCompanysLeader(this);
    }
}

class ThorinCompanysLeaderTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DWARF, "Dwarf you control");

    ThorinCompanysLeaderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken(), 2));
        setTriggerPhrase("Whenever a Dwarf you control deals combat damage to a player or battle, ");
    }

    private ThorinCompanysLeaderTriggeredAbility(final ThorinCompanysLeaderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThorinCompanysLeaderTriggeredAbility copy() {
        return new ThorinCompanysLeaderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damagedEvent = (DamagedEvent) event;
        if (!damagedEvent.isCombatDamage()) {
            return false;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }

        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            Permanent damagedPermanent = game.getPermanent(event.getTargetId());
            if (damagedPermanent == null || !damagedPermanent.isBattle(game)) {
                return false;
            }
        }

        getAllEffects().setValue("damage", event.getAmount());
        return true;
    }
}
