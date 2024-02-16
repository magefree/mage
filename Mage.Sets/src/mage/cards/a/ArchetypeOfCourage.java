package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ArchetypeOfCourage extends CardImpl {

    public ArchetypeOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control have first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures your opponents control lose first strike and can't have or gain first strike.
        this.addAbility(new SimpleStaticAbility(new CreaturesCantGetOrHaveAbilityEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield)));
    }

    private ArchetypeOfCourage(final ArchetypeOfCourage card) {
        super(card);
    }

    @Override
    public ArchetypeOfCourage copy() {
        return new ArchetypeOfCourage(this);
    }
}
