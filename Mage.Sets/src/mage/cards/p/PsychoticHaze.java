
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class PsychoticHaze extends CardImpl {

    public PsychoticHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");


        // Psychotic Haze deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1));

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private PsychoticHaze(final PsychoticHaze card) {
        super(card);
    }

    @Override
    public PsychoticHaze copy() {
        return new PsychoticHaze(this);
    }
}
