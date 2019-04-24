package mage.cards.d;

import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DavrielsShadowfugue extends CardImpl {

    public DavrielsShadowfugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target player discards two cards and loses 2 life.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DavrielsShadowfugue(final DavrielsShadowfugue card) {
        super(card);
    }

    @Override
    public DavrielsShadowfugue copy() {
        return new DavrielsShadowfugue(this);
    }
}
