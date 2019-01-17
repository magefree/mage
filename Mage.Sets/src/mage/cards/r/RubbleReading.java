package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubbleReading extends CardImpl {

    public RubbleReading(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy target land. Scry 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(2));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private RubbleReading(final RubbleReading card) {
        super(card);
    }

    @Override
    public RubbleReading copy() {
        return new RubbleReading(this);
    }
}
