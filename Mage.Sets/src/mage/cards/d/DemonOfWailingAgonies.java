
package mage.cards.d;

import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DemonOfWailingAgonies extends CardImpl {

    public DemonOfWailingAgonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lieutenant - As long as you control your commander, Demon of Wailing Agonies gets +2/+2 and has "Whenever Demon of Wailing Agonies deals combat damage to a player, that player sacrifices a creature."
        this.addAbility(new LieutenantAbility(new GainAbilitySourceEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeEffect(
                        StaticFilters.FILTER_PERMANENT_CREATURE, 1, "that player"
                ), false, true)
        ), "and has \"Whenever {this} deals combat damage to a player, that player sacrifices a creature.\""));
    }

    private DemonOfWailingAgonies(final DemonOfWailingAgonies card) {
        super(card);
    }

    @Override
    public DemonOfWailingAgonies copy() {
        return new DemonOfWailingAgonies(this);
    }
}
