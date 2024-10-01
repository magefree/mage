package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author cbt33
 */
public final class ZombieCannibal extends CardImpl {

    private static final FilterCard filterGraveyardCard = new FilterCard("card from that player's graveyard");
    public ZombieCannibal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zombie Cannibal deals combat damage to a player, you may exile target card from that player's graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, true, true);
        ability.addTarget(new TargetCardInGraveyard(filterGraveyardCard));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private ZombieCannibal(final ZombieCannibal card) {
        super(card);
    }

    @Override
    public ZombieCannibal copy() {
        return new ZombieCannibal(this);
    }
}
