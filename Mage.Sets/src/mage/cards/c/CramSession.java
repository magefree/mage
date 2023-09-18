package mage.cards.c;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CramSession extends CardImpl {

    public CramSession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B/G}");

        // You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private CramSession(final CramSession card) {
        super(card);
    }

    @Override
    public CramSession copy() {
        return new CramSession(this);
    }
}
