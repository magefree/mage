package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Chatterstorm extends CardImpl {

    public Chatterstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Create a 1/1 green Squirrel creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken()));

        // Storm
        this.addAbility(new StormAbility());
    }

    private Chatterstorm(final Chatterstorm card) {
        super(card);
    }

    @Override
    public Chatterstorm copy() {
        return new Chatterstorm(this);
    }
}
