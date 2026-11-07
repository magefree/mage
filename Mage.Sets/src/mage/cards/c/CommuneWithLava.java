package mage.cards.c;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CommuneWithLava extends CardImpl {

    public CommuneWithLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Exile the top X cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(GetXValue.instance, false, Duration.UntilEndOfYourNextTurn));
    }

    private CommuneWithLava(final CommuneWithLava card) {
        super(card);
    }

    @Override
    public CommuneWithLava copy() {
        return new CommuneWithLava(this);
    }
}

