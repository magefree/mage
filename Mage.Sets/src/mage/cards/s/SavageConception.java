
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author Plopman
 */
public final class SavageConception extends CardImpl {

    public SavageConception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // Create a 3/3 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken()));
        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private SavageConception(final SavageConception card) {
        super(card);
    }

    @Override
    public SavageConception copy() {
        return new SavageConception(this);
    }
}
