package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class ArchetypeOfFinality extends CardImpl {

    public ArchetypeOfFinality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.GORGON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Creatures you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures your opponents control lose deathtouch and can't have or gain deathtouch.
        this.addAbility(new SimpleStaticAbility(new CreaturesCantGetOrHaveAbilityEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield)));
    }

    private ArchetypeOfFinality(final ArchetypeOfFinality card) {
        super(card);
    }

    @Override
    public ArchetypeOfFinality copy() {
        return new ArchetypeOfFinality(this);
    }
}
