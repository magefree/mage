package mage.cards.h;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntForSpecimens extends CardImpl {

    public HuntForSpecimens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Pest11GainLifeToken()));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private HuntForSpecimens(final HuntForSpecimens card) {
        super(card);
    }

    @Override
    public HuntForSpecimens copy() {
        return new HuntForSpecimens(this);
    }
}
