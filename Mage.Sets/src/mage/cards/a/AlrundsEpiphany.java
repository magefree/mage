package mage.cards.a;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BlueBirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlrundsEpiphany extends CardImpl {

    public AlrundsEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Create two 1/1 blue Bird creature tokens with flying. Take an extra turn after this one. Exile Alrund's Epiphany.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlueBirdToken(), 2));
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Foretell {4}{U}{U}
        this.addAbility(new ForetellAbility(this, "{4}{U}{U}"));
    }

    private AlrundsEpiphany(final AlrundsEpiphany card) {
        super(card);
    }

    @Override
    public AlrundsEpiphany copy() {
        return new AlrundsEpiphany(this);
    }
}
