

package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class Phytoburst extends CardImpl {

    public Phytoburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Target creature gets +5/+5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(5,5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Phytoburst(final Phytoburst card) {
        super(card);
    }

    @Override
    public Phytoburst copy() {
        return new Phytoburst(this);
    }

}
