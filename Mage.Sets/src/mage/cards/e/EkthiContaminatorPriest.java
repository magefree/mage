package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Meowcelina
 */
public final class EkthiContaminatorPriest extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PHYREXIAN, "Phyrexians");

    public EkthiContaminatorPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Phyrexians you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
        // Each Equipment you control has living weapon.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new LivingWeaponAbility(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT,
                "Each Equipment you control has living weapon. <i>(Whenever an Equipment you control enters, create a 0/0 black Phyrexian Germ creature token, then attach that Equipment to it.)</i>"
        )));
    }

    private EkthiContaminatorPriest(final EkthiContaminatorPriest card) {
        super(card);
    }

    @Override
    public EkthiContaminatorPriest copy() {
        return new EkthiContaminatorPriest(this);
    }
}
