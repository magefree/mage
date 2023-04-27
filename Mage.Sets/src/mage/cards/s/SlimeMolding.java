
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;

/**
 *
 * @author Plopman
 */
public final class SlimeMolding extends CardImpl {

    public SlimeMolding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}");


        // Create an X/X green Ooze creature token.
        this.getSpellAbility().addEffect(new SlimeMoldingEffect());
    }

    private SlimeMolding(final SlimeMolding card) {
        super(card);
    }

    @Override
    public SlimeMolding copy() {
        return new SlimeMolding(this);
    }
}

class SlimeMoldingEffect extends OneShotEffect {

    public SlimeMoldingEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Ooze creature token";
    }

    public SlimeMoldingEffect(SlimeMoldingEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = source.getManaCostsToPay().getX();

        OozeToken oozeToken = new OozeToken();
        oozeToken.setPower(count);
        oozeToken.setToughness(count);
        oozeToken.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

    @Override
    public SlimeMoldingEffect copy() {
        return new SlimeMoldingEffect(this);
    }
}
