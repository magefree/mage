package mage.cards.a;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AlmsOfTheVein extends CardImpl {

    public AlmsOfTheVein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent loses 3 life and you gain 3 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Madness {B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}")));
    }

    private AlmsOfTheVein(final AlmsOfTheVein card) {
        super(card);
    }

    @Override
    public AlmsOfTheVein copy() {
        return new AlmsOfTheVein(this);
    }
}
