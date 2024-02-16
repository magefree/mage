package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class ArchetypeOfAggression extends CardImpl {

    public ArchetypeOfAggression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures your opponents control lose trample and can't have or gain trample.
        this.addAbility(new SimpleStaticAbility(new CreaturesCantGetOrHaveAbilityEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield)));
    }

    private ArchetypeOfAggression(final ArchetypeOfAggression card) {
        super(card);
    }

    @Override
    public ArchetypeOfAggression copy() {
        return new ArchetypeOfAggression(this);
    }
}
