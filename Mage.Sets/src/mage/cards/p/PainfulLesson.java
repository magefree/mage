package mage.cards.p;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PainfulLesson extends CardImpl {

    public PainfulLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player draws two cards and loses 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and"));
    }

    private PainfulLesson(final PainfulLesson card) {
        super(card);
    }

    @Override
    public PainfulLesson copy() {
        return new PainfulLesson(this);
    }
}
