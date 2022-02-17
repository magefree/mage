
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ExavaRakdosBloodWitch extends CardImpl {

    static final String rule = "Each other creature you control with a +1/+1 counter on it has haste";
    public ExavaRakdosBloodWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Unleash
        this.addAbility(new UnleashAbility());
        // Each other creature you control with a +1/+1 counter on it has haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1,
                rule)));

    }

    private ExavaRakdosBloodWitch(final ExavaRakdosBloodWitch card) {
        super(card);
    }

    @Override
    public ExavaRakdosBloodWitch copy() {
        return new ExavaRakdosBloodWitch(this);
    }
}
