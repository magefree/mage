package mage.cards.b;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodPact extends CardImpl {

    public BloodPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target player draws two cards and loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BloodPact(final BloodPact card) {
        super(card);
    }

    @Override
    public BloodPact copy() {
        return new BloodPact(this);
    }
}
