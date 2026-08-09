package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author muz
 */
public final class MultiversalIncursion extends CardImpl {

    public MultiversalIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // For each nontoken creature you control, create a token that's a copy of that creature, except it isn't legendary.
        this.getSpellAbility().addEffect(new MultiversalIncursionEffect());
    }

    private MultiversalIncursion(final MultiversalIncursion card) {
        super(card);
    }

    @Override
    public MultiversalIncursion copy() {
        return new MultiversalIncursion(this);
    }
}

class MultiversalIncursionEffect extends OneShotEffect {

    MultiversalIncursionEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "For each nontoken creature you control, create a token that's a copy of that creature, except it isn't legendary";
    }

    private MultiversalIncursionEffect(final MultiversalIncursionEffect effect) {
        super(effect);
    }

    @Override
    public MultiversalIncursionEffect copy() {
        return new MultiversalIncursionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>(game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, source.getControllerId(), source, game
        ));
        for (Permanent permanent : permanents) {
            new CreateTokenCopyTargetEffect()
                .setSavedPermanent(permanent)
                .setIsntLegendary(true)
                .apply(game, source);
        }
        return true;
    }
}
