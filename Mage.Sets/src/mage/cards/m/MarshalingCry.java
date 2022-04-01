
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;

/**
 *
 * @author fireshoes
 */
public final class MarshalingCry extends CardImpl {
    
    public MarshalingCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");

        // Creatures you control get +1/+1 and gain vigilance until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +1/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gain vigilance until end of turn");
        this.getSpellAbility().addEffect(effect);
        
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
        
        // Flashback {3}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{W}")));
    }

    private MarshalingCry(final MarshalingCry card) {
        super(card);
    }

    @Override
    public MarshalingCry copy() {
        return new MarshalingCry(this);
    }
}
