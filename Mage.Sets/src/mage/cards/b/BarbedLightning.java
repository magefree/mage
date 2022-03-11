
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class BarbedLightning extends CardImpl {

    public BarbedLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one - Barbed Lightning deals 3 damage to target creature;
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // or Barbed Lightning deals 3 damage to target player.
        Mode mode = new Mode(new DamageTargetEffect(3));
        mode.addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private BarbedLightning(final BarbedLightning card) {
        super(card);
    }

    @Override
    public BarbedLightning copy() {
        return new BarbedLightning(this);
    }
}
