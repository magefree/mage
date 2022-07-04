package mage.cards.a;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtrociousExperiment extends CardImpl {

    public AtrociousExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player mills two cards, draws two cards, and loses 2 life.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(2));
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2).setText(", draws two cards"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText(", and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private AtrociousExperiment(final AtrociousExperiment card) {
        super(card);
    }

    @Override
    public AtrociousExperiment copy() {
        return new AtrociousExperiment(this);
    }
}
