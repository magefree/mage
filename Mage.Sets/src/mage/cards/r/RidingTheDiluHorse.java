
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RidingTheDiluHorse extends CardImpl {

    public RidingTheDiluHorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Target creature gets +2/+2 and gains horsemanship.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityTargetEffect(HorsemanshipAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("and gains horsemanship");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RidingTheDiluHorse(final RidingTheDiluHorse card) {
        super(card);
    }

    @Override
    public RidingTheDiluHorse copy() {
        return new RidingTheDiluHorse(this);
    }
}
