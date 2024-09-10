package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EnduringGlimmerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringCourage extends CardImpl {

    public EnduringCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature you control enters, it gets +2/+0 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new BoostTargetEffect(2, 0).setText("it gets +2/+0"),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and gains haste until end of turn"));
        this.addAbility(ability);

        // When Enduring Courage dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new EnduringGlimmerTriggeredAbility());
    }

    private EnduringCourage(final EnduringCourage card) {
        super(card);
    }

    @Override
    public EnduringCourage copy() {
        return new EnduringCourage(this);
    }
}
