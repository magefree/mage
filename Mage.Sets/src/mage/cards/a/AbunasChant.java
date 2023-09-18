
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class AbunasChant extends CardImpl {

    public AbunasChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        //You gain 5 life; 
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
        //or prevent the next 5 damage that would be dealt to target creature this turn.
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, 5));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private AbunasChant(final AbunasChant card) {
        super(card);
    }

    @Override
    public AbunasChant copy() {
        return new AbunasChant(this);
    }
}
