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

// Based on WrathfulRaptorsTriggeredAbility
class DonnaNobleTriggeredAbility extends TriggeredAbilityImpl {

    DonnaNobleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DonnaNobleEffect());
        this.addTarget(new TargetOpponent());
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
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent damagedPermanent = game.getPermanent(event.getTargetId());
        int damage = event.getAmount();
        if (damagedPermanent == null || damage < 1) {
            return false;
        }

        Permanent paired = null;
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getPairedCard() != null) {
            paired = permanent.getPairedCard().getPermanent(game);
        }
        boolean isPaired = paired != null && paired.getPairedCard() != null &&
                paired.getPairedCard().equals(new MageObjectReference(permanent, game));

        if (getSourceId().equals(event.getTargetId()) || (isPaired && paired.getId().equals(event.getTargetId()))){
            this.getEffects().setValue("damagedPermanentUUID", event.getTargetId());
            this.getEffects().setValue("damage", damage);
            return true;
        }
        return false;
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
        Permanent damagedPermanent = game.getPermanent((UUID) getValue("damagedPermanentUUID"));
        Integer damage = (Integer) getValue("damage");
        if (damagedPermanent == null || damage == null) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.damage(damage, source.getSourcePermanentOrLKI(game).getId(), source, game) > 0;
        }
        return false;
    }
}