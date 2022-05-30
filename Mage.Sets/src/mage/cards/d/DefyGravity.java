
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DefyGravity extends CardImpl {

    public DefyGravity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target creature gains flying until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Flashback {U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{U}")));
    }

    private DefyGravity(final DefyGravity card) {
        super(card);
    }

    @Override
    public DefyGravity copy() {
        return new DefyGravity(this);
    }
}
