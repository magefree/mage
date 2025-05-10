package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ARealmReborn extends CardImpl {

    public ARealmReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Other permanents you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS, true
        )));
    }

    private ARealmReborn(final ARealmReborn card) {
        super(card);
    }

    @Override
    public ARealmReborn copy() {
        return new ARealmReborn(this);
    }
}
