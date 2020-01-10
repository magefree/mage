package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SatyrCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SatyrsCunning extends CardImpl {

    public SatyrsCunning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Create a 1/1 red Satyr creature token with "This creature can't block."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SatyrCantBlockToken()));

        // Escape—{2}{R}, Exile two other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{R}", 2));
    }

    private SatyrsCunning(final SatyrsCunning card) {
        super(card);
    }

    @Override
    public SatyrsCunning copy() {
        return new SatyrsCunning(this);
    }
}
