package mage.cards.i;

import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntroductionToAnnihilation extends CardImpl {

    public IntroductionToAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}");

        this.subtype.add(SubType.LESSON);

        // Exile target nonland permanent. Its controller draws a card.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardTargetControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private IntroductionToAnnihilation(final IntroductionToAnnihilation card) {
        super(card);
    }

    @Override
    public IntroductionToAnnihilation copy() {
        return new IntroductionToAnnihilation(this);
    }
}
