package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.FractalToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BodyOfResearch extends CardImpl {

    public BodyOfResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}{G}{U}{U}{U}");

        // Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is the number of cards in your library.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                BodyOfResearchValue.instance, "Put X +1/+1 counters on it, " +
                        "where X is the number of cards in your library"
        ));
    }

    private BodyOfResearch(final BodyOfResearch card) {
        super(card);
    }

    @Override
    public BodyOfResearch copy() {
        return new BodyOfResearch(this);
    }
}

enum BodyOfResearchValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player != null ? player.getLibrary().size() : 0;
    }

    @Override
    public BodyOfResearchValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
