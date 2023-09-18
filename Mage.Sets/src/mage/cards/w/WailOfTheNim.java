
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.RegenerateAllEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WailOfTheNim extends CardImpl {

    public WailOfTheNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Choose one - Regenerate each creature you control;
        this.getSpellAbility().addEffect(new RegenerateAllEffect(new FilterControlledCreaturePermanent()));
        
        // or Wail of the Nim deals 1 damage to each creature and each player.
        Mode mode = new Mode(new DamageEverythingEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {B}
        this.addAbility(new EntwineAbility("{B}"));
    }

    private WailOfTheNim(final WailOfTheNim card) {
        super(card);
    }

    @Override
    public WailOfTheNim copy() {
        return new WailOfTheNim(this);
    }
}
