package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CommanderStormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.AngelToken;

/**
 *
 * @author TheElk801
 */
public final class EmpyrialStorm extends CardImpl {

    public EmpyrialStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // When you cast this spell, copy it for each time you've cast your commander from the command zone this game.
        this.addAbility(new CommanderStormAbility());

        // Create a 4/4 white Angel creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken()));
    }

    private EmpyrialStorm(final EmpyrialStorm card) {
        super(card);
    }

    @Override
    public EmpyrialStorm copy() {
        return new EmpyrialStorm(this);
    }
}
