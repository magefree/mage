
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class AbsoluteLaw extends CardImpl {

    public AbsoluteLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        Ability ability = ProtectionAbility.from(ObjectColor.RED);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, false)));
    }

    private AbsoluteLaw(final AbsoluteLaw card) {
        super(card);
    }

    @Override
    public AbsoluteLaw copy() {
        return new AbsoluteLaw(this);
    }

}
