
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ExpansionSymbolToken;

/**
 *
 * @author L_J
 */
public class SymbolStatus extends CardImpl {

    public SymbolStatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Create a 1/1 colorless Expansion-Symbol creature token for each different expansion symbol among permanents you control.
        this.getSpellAbility().addEffect(new SymbolStatusEffect());
    }

    private SymbolStatus(final SymbolStatus card) {
        super(card);
    }

    @Override
    public SymbolStatus copy() {
        return new SymbolStatus(this);
    }
}

class SymbolStatusEffect extends OneShotEffect {

    public SymbolStatusEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 1/1 colorless Expansion-Symbol creature token for each different expansion symbol among permanents you control";
    }

    private SymbolStatusEffect(final SymbolStatusEffect effect) {
        super(effect);
    }

    @Override
    public SymbolStatusEffect copy() {
        return new SymbolStatusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<String> symbols = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            symbols.add(permanent.getExpansionSetCode());
        }
        return new CreateTokenEffect(new ExpansionSymbolToken(), symbols.size()).apply(game, source);
    }
}
