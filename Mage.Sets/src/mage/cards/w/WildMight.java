
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class WildMight extends CardImpl {

    public WildMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target creature gets +1/+1 until end of turn. That creature gets an additional +4/+4 until end of turn unless any player pays {2}.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        Effect effect = new  DoUnlessAnyPlayerPaysEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        effect.setText("That creature gets an additional +4/+4 until end of turn unless any player pays {2}");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WildMight(final WildMight card) {
        super(card);
    }

    @Override
    public WildMight copy() {
        return new WildMight(this);
    }
}
