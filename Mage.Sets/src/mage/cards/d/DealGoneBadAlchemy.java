package mage.cards.d;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 * @author chesse20
 */
public final class DealGoneBadAlchemy extends CardImpl {

    public DealGoneBadAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Target creature gets -3/-3 until end of turn. Target player mills three cards.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3)
                .setText("Target player mills three cards")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));//add 3 life
        
    }

    private DealGoneBadAlchemy(final DealGoneBadAlchemy card) {
        super(card);
    }

    @Override
    public DealGoneBadAlchemy copy() {
        return new DealGoneBadAlchemy(this);
    }
}
