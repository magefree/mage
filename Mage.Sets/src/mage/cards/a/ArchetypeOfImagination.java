package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ArchetypeOfImagination extends CardImpl {

    public ArchetypeOfImagination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures your opponents control lose flying and can't have or gain flying.
        this.addAbility(new SimpleStaticAbility(new CreaturesCantGetOrHaveAbilityEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield)));
    }

    private ArchetypeOfImagination(final ArchetypeOfImagination card) {
        super(card);
    }

    @Override
    public ArchetypeOfImagination copy() {
        return new ArchetypeOfImagination(this);
    }
}
