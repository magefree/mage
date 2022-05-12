
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.WeirdToken;

/**
 *
 * @author nigelzor
 */
public final class Thunderheads extends CardImpl {

    public Thunderheads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Replicate {2}{U}
        this.addAbility(new ReplicateAbility("{2}{U}"));
        // Create a 3/3 blue Weird creature token with defender and flying. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ThunderheadsEffect());
    }

    private Thunderheads(final Thunderheads card) {
        super(card);
    }

    @Override
    public Thunderheads copy() {
        return new Thunderheads(this);
    }
}

class ThunderheadsEffect extends OneShotEffect {

    public ThunderheadsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 3/3 blue Weird creature token with defender and flying. Exile it at the beginning of the next end step.";
    }

    public ThunderheadsEffect(ThunderheadsEffect effect) {
        super(effect);
    }

    @Override
    public ThunderheadsEffect copy() {
        return new ThunderheadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new WeirdToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
