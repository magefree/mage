
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RoarOfTheKha extends CardImpl {
    
    private static final String rule = "untap all creatures you control";

    public RoarOfTheKha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Choose one - Creatures you control get +1/+1 until end of turn;
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        
        // or untap all creatures you control.
        Mode mode = new Mode(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), rule));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {1}{W}
        this.addAbility(new EntwineAbility("{1}{W}"));
    }

    private RoarOfTheKha(final RoarOfTheKha card) {
        super(card);
    }

    @Override
    public RoarOfTheKha copy() {
        return new RoarOfTheKha(this);
    }
}
