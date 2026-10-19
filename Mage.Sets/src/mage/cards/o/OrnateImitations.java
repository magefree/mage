package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

public final class OrnateImitations extends CardImpl {

    public OrnateImitations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{U}");

        // For each number between 1 and X, conjure a duplicate of a random creature card with that mana value onto the battlefield. X can't be 0.
        this.getSpellAbility().addEffect(new OrnateImitationsEffect());
        CardUtil.castStream(this.getSpellAbility().getManaCosts(), VariableManaCost.class)
                .forEach(cost -> cost.setMinX(1));
    }

    private OrnateImitations(final OrnateImitations card) {
        super(card);
    }

    @Override
    public OrnateImitations copy() {
        return new OrnateImitations(this);
    }
}

class OrnateImitationsEffect extends OneShotEffect {

    OrnateImitationsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each number between 1 and X, conjure a duplicate of a random creature card "
                + "with that mana value onto the battlefield. X can't be 0";
    }

    private OrnateImitationsEffect(final OrnateImitationsEffect effect) {
        super(effect);
    }

    @Override
    public OrnateImitationsEffect copy() {
        return new OrnateImitationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int x = CardUtil.getSourceCostsTag(game, source, "X", 0);
        for (int manaValue = 1; manaValue <= x; manaValue++) {
            ConjureRandomCreatureEffect.conjure(manaValue, Zone.BATTLEFIELD, game, source);
        }
        return true;
    }
}
