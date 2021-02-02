
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class DemonOfWailingAgonies extends CardImpl {

    public DemonOfWailingAgonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Lieutenant - As long as you control your commander, Demon of Wailing Agonies gets +2/+2 and has "Whenever Demon of Wailing Agonies deals combat damage to a player, that player sacrifices a creature."
        Ability gainedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, 1, "that player"), false, true);
        ContinuousEffect effect = new GainAbilitySourceEffect(gainedAbility);
        effect.setText("and has \"Whenever {this} deals combat damage to a player, that player sacrifices a creature.\"");
        this.addAbility(new LieutenantAbility(effect));
    }

    private DemonOfWailingAgonies(final DemonOfWailingAgonies card) {
        super(card);
    }

    @Override
    public DemonOfWailingAgonies copy() {
        return new DemonOfWailingAgonies(this);
    }
}
