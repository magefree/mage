package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TicketBoothTunnelOfHate extends RoomCard {

    public TicketBoothTunnelOfHate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{R}", "{4}{R}{R}");

        // Ticket Booth
        // When you unlock this door, manifest dread.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new ManifestDreadEffect(), false, true));

        // Tunnel of Hate
        // Whenever you attack, target attacking creature gains double strike until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()), 1);
        ability.addTarget(new TargetAttackingCreature());
        this.getRightHalfCard().addAbility(ability);
    }

    private TicketBoothTunnelOfHate(final TicketBoothTunnelOfHate card) {
        super(card);
    }

    @Override
    public TicketBoothTunnelOfHate copy() {
        return new TicketBoothTunnelOfHate(this);
    }
}
