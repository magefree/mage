
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author noxx
 */
public final class TemporalMastery extends CardImpl {

    public TemporalMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}{U}");


        // Take an extra turn after this one. Exile Temporal Mastery.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Miracle {1}{U}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{1}{U}")));
    }

    private TemporalMastery(final TemporalMastery card) {
        super(card);
    }

    @Override
    public TemporalMastery copy() {
        return new TemporalMastery(this);
    }
}
