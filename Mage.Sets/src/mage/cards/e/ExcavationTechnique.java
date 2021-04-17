package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExcavationTechnique extends CardImpl {

    public ExcavationTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Destroy target nonland permanent. Its controller creates two Treasure tokens.
        this.getSpellAbility().addEffect(new ExcavationTechniqueEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ExcavationTechnique(final ExcavationTechnique card) {
        super(card);
    }

    @Override
    public ExcavationTechnique copy() {
        return new ExcavationTechnique(this);
    }
}

class ExcavationTechniqueEffect extends OneShotEffect {

    ExcavationTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target nonland permanent. Its controller creates two Treasure tokens";
    }

    private ExcavationTechniqueEffect(final ExcavationTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public ExcavationTechniqueEffect copy() {
        return new ExcavationTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        new TreasureToken().putOntoBattlefield(2, game, source, permanent.getControllerId());
        return true;
    }
}
