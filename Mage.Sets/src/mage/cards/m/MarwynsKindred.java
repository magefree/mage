package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;
import java.util.UUID;

/**
 * @author karapuzz14
 */
public final class MarwynsKindred extends CardImpl {


    public MarwynsKindred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{G}{G}");

        // Conjure a card named Marwyn, the Nurturer and X cards named Llanowar Elves onto the battlefield.
        this.getSpellAbility().addEffect(new MarwynsKindredEffect());
    }

    private MarwynsKindred(final MarwynsKindred card) {
        super(card);
    }

    @Override
    public mage.cards.m.MarwynsKindred copy() {
        return new MarwynsKindred(this);
    }
}

class MarwynsKindredEffect extends OneShotEffect {


    MarwynsKindredEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "conjure a card named Marwyn, the Nurturer and X cards named Llanowar Elves onto the battlefield";
    }

    private MarwynsKindredEffect(final MarwynsKindredEffect effect) {
        super(effect);
    }

    @Override
    public MarwynsKindredEffect copy() {
        return new MarwynsKindredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        ConjureCardEffect conjureMarwynCardEffect = new ConjureCardEffect("Marwyn, the Nurturer",
                Zone.BATTLEFIELD, 1);

        int amount = CardUtil.getSourceCostsTag(game, source, "X", 0);
        ConjureCardEffect conjureElvesCardEffect = new ConjureCardEffect("Llanowar Elves",
                Zone.BATTLEFIELD, amount);

        return conjureMarwynCardEffect.apply(game, source) && conjureElvesCardEffect.apply(game, source);
    }
}
