package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.*;
import mage.abilities.keyword.SoulbondAbility;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.DamagedBatchForPermanentsEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
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
        Ability ability = new DonnaNobleTriggeredAbility();
        this.addAbility(ability);

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

// Based on DealtDamageToSourceTriggeredAbility
class DonnaNobleTriggeredAbility extends TriggeredAbilityImpl {

    DonnaNobleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DonnaNobleEffect());
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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PERMANENTS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getTargets().clear();
        DamagedBatchForPermanentsEvent dEvent = (DamagedBatchForPermanentsEvent) event;
        return checkTriggerThis(dEvent) || checkTriggerPaired(dEvent, game);
    }

    boolean checkTriggerThis(DamagedBatchForPermanentsEvent dEvent) {
        this.getEffects().setValue("damageToThis", null);
        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> getSourceId().equals(damagedEvent.getTargetId()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }
        this.getEffects().setValue("damageToThis", damage);
        this.addTarget(new TargetOpponent());
        return true;
    }

    boolean checkTriggerPaired(DamagedBatchForPermanentsEvent dEvent, Game game) {

        this.getEffects().setValue("damageToPaired", null);

        Permanent paired;
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getPairedCard() != null) {
            paired = permanent.getPairedCard().getPermanent(game);
            if (paired == null || paired.getPairedCard() == null || !paired.getPairedCard().equals(new MageObjectReference(permanent, game))) {
                return false;
            }
        } else {
            paired = null;
        }

        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> paired != null && paired.getId().equals(damagedEvent.getTargetId()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }
        this.getEffects().setValue("damageToPaired", damage);
        this.addTarget(new TargetOpponent());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or a creature it's paired with is dealt damage, " +
                "{this} deals that much damage to target opponent.";
    }
}

//Based on WrathfulRaptorsEffect
class DonnaNobleEffect extends OneShotEffect {

    DonnaNobleEffect() {
        super(Outcome.Benefit);
    }

    private DonnaNobleEffect(final DonnaNobleEffect effect) {
        super(effect);
    }

    @Override
    public DonnaNobleEffect copy() {
        return new DonnaNobleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // Only resolve the targets we need.
        int targetIdx = 0;
        boolean damageApplied = false;

        Integer[] damages = {
                (Integer) getValue("damageToThis"),
                (Integer) getValue("damageToPaired")
        };

        for (Integer damage : damages){
            if (damage == null) {
                continue;
            }
            UUID targetId = source.getTargets().get(targetIdx++).getFirstTarget();
            Player player = game.getPlayer(targetId);
            UUID sourceId = source.getSourcePermanentOrLKI(game).getId();
            if (player != null && sourceId != null) {
                damageApplied |= player.damage(damage, sourceId, source, game) > 0;
            }
        }

        return damageApplied;
    }
}