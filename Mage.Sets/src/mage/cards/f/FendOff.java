package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class FendOff extends CardImpl {
    
    private static final String rule = "Prevent all combat damage that would be dealt by target creature this turn.";
    
    public FendOff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent all combat damage that would be dealt by target creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true).setText(rule));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
        
    }
    
    private FendOff(final FendOff card) {
        super(card);
    }
    
    @Override
    public FendOff copy() {
        return new FendOff(this);
    }
}
