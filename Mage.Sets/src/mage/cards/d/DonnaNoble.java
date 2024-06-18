package mage.cards.d;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author jimga150
 */
public final class DonnaNoble extends CardImpl {

    public DonnaNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // Whenever Donna or a creature it's paired with is dealt damage, Donna deals that much damage to target opponent.
        this.addAbility(new DonnaNobleTriggeredAbility());

        // If Donna is paired with another creature and they are both dealt damage at the same time,
        // the second ability triggers twice. (2023-10-13)

        // Donna's ability triggers when either creature is dealt damage even if one or both were dealt lethal damage.
        // (2023-10-13)

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private DonnaNoble(final DonnaNoble card) {
        super(card);
    }

    @Override
    public DonnaNoble copy() {
        return new DonnaNoble(this);
    }
}

// Based on DealtDamageToSourceTriggeredAbility, except this uses DamagedBatchForOnePermanentEvent,
// which batches all damage dealt at the same time on a permanent-by-permanent basis
class DonnaNobleTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    DonnaNobleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH));
        this.addTarget(new TargetOpponent());
        this.setTriggerPhrase("Whenever {this} or a creature it's paired with is dealt damage, ");
    }

    private DonnaNobleTriggeredAbility(final DonnaNobleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DonnaNobleTriggeredAbility copy() {
        return new DonnaNobleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (permanent == null) {
            return Stream.empty();
        }
        Set<UUID> permanentsLookedFor = new HashSet<>();
        permanentsLookedFor.add(permanent.getId());
        if (permanent.getPairedCard() != null) {
            Permanent paired = permanent.getPairedCard().getPermanentOrLKIBattlefield(game);
            if (paired != null) {
                permanentsLookedFor.add(paired.getId());
            }
        }
        return ((DamagedBatchForOnePermanentEvent) event)
                .getEvents()
                .stream()
                .filter(e -> e.getAmount() > 0)
                .filter(e -> permanentsLookedFor.contains(e.getTargetId()));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = filterBatchEvent(event, game)
                .mapToInt(DamagedPermanentEvent::getAmount)
                .sum();
        if (amount <= 0) {
            return false;
        }
        this.getEffects().setValue("damage", amount);
        return true;
    }
}
