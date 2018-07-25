package mage.cards.d;

import java.util.UUID;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.SalvageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class DefenseMatrix extends CardImpl {

    public DefenseMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Prevent all damage that would be dealt to target creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Salvage {3}
        this.addAbility(new SalvageAbility(new ManaCostsImpl("{3}")));
    }

    public DefenseMatrix(final DefenseMatrix card) {
        super(card);
    }

    @Override
    public DefenseMatrix copy() {
        return new DefenseMatrix(this);
    }
}
