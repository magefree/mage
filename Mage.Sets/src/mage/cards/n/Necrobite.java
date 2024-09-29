
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx

 */
public final class Necrobite extends CardImpl {

    public Necrobite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");


        // Target creature gains deathtouch until end of turn. Regenerate it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new RegenerateTargetEffect().setText("Regenerate it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Necrobite(final Necrobite card) {
        super(card);
    }

    @Override
    public Necrobite copy() {
        return new Necrobite(this);
    }
}
