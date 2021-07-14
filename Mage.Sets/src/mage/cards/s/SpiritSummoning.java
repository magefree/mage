package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Spirit32Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritSummoning extends CardImpl {

    public SpiritSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R/W}{R/W}");

        this.subtype.add(SubType.LESSON);

        // Create a 3/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit32Token()));
    }

    private SpiritSummoning(final SpiritSummoning card) {
        super(card);
    }

    @Override
    public SpiritSummoning copy() {
        return new SpiritSummoning(this);
    }
}
