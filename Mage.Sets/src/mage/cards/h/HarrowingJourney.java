package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class HarrowingJourney extends CardImpl {

    public HarrowingJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Target player draws three cards and loses 3 life.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3).setText("and loses 3 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HarrowingJourney(final HarrowingJourney card) {
        super(card);
    }

    @Override
    public HarrowingJourney copy() {
        return new HarrowingJourney(this);
    }
}
