package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
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
public final class ArchetypeOfEndurance extends CardImpl {

    public ArchetypeOfEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Creatures you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures your opponents control lose hexproof and can't have or gain hexproof.
        this.addAbility(new SimpleStaticAbility(new CreaturesCantGetOrHaveAbilityEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield)));
    }

    private ArchetypeOfEndurance(final ArchetypeOfEndurance card) {
        super(card);
    }

    @Override
    public ArchetypeOfEndurance copy() {
        return new ArchetypeOfEndurance(this);
    }
}
