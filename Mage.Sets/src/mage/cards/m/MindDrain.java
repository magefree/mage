package mage.cards.m;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindDrain extends CardImpl {

    public MindDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent discards two cards, mills a card, and loses 1 life. You gain 1 life.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(1).setText(", mills a card"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1).setText(", and loses 1 life."));
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private MindDrain(final MindDrain card) {
        super(card);
    }

    @Override
    public MindDrain copy() {
        return new MindDrain(this);
    }
}
