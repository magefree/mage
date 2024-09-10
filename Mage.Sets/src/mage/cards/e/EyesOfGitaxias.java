package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyesOfGitaxias extends CardImpl {

    public EyesOfGitaxias(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Incubate 3.
        this.getSpellAbility().addEffect(new IncubateEffect(3));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EyesOfGitaxias(final EyesOfGitaxias card) {
        super(card);
    }

    @Override
    public EyesOfGitaxias copy() {
        return new EyesOfGitaxias(this);
    }
}
