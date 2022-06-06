
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class IchorSlick extends CardImpl {

    public IchorSlick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

        // Madness {3}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private IchorSlick(final IchorSlick card) {
        super(card);
    }

    @Override
    public IchorSlick copy() {
        return new IchorSlick(this);
    }
}
