package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class GravitySphere extends CardImpl {

    public GravitySphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        addSuperType(SuperType.WORLD);

        // All creatures lose flying.
        Effect effect = new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_CREATURES);
        effect.setText("All creatures lose flying");
        this.addAbility(new SimpleStaticAbility(effect));

    }

    private GravitySphere(final GravitySphere card) {
        super(card);
    }

    @Override
    public GravitySphere copy() {
        return new GravitySphere(this);
    }
}
