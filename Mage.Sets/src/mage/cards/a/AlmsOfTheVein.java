
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class AlmsOfTheVein extends CardImpl {

    public AlmsOfTheVein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent loses 3 life and you gain 3 life.
        Effect effect = new LoseLifeTargetEffect(3);
        effect.setText("Target opponent loses 3 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());
        effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        this.getSpellAbility().addEffect(effect);

        // Madness {B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{B}")));
    }

    public AlmsOfTheVein(final AlmsOfTheVein card) {
        super(card);
    }

    @Override
    public AlmsOfTheVein copy() {
        return new AlmsOfTheVein(this);
    }
}
