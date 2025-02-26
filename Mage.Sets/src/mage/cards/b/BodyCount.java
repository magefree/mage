package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlDiedCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BodyCount extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures that died under your control this turn", CreaturesYouControlDiedCount.instance
    );

    public BodyCount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Draw a card for each creature that died under your control this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CreaturesYouControlDiedCount.instance));
        this.getSpellAbility().addHint(hint);

        // Spectacle {B}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{B}")));
    }

    private BodyCount(final BodyCount card) {
        super(card);
    }

    @Override
    public BodyCount copy() {
        return new BodyCount(this);
    }
}