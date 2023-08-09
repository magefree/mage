
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class MentalAgony extends CardImpl {

    public MentalAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Target player discards two cards and loses 2 life.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MentalAgony(final MentalAgony card) {
        super(card);
    }

    @Override
    public MentalAgony copy() {
        return new MentalAgony(this);
    }
}
