package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class GoblinNegotiation extends CardImpl {

    public GoblinNegotiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Goblin Negotiation deals X damage to target creature. Create a number of 1/1 red Goblin creature tokens equal to the amount of excess damage dealt to that creature this way.
        this.getSpellAbility().addEffect(new GoblinNegotiationEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GoblinNegotiation(final GoblinNegotiation card) {
        super(card);
    }

    @Override
    public GoblinNegotiation copy() {
        return new GoblinNegotiation(this);
    }
}

class GoblinNegotiationEffect extends OneShotEffect {

    GoblinNegotiationEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to target creature. Create a number of "
                + "1/1 red Goblin creature tokens equal to the amount of "
                + "excess damage dealt to that creature this way";
    }

    private GoblinNegotiationEffect(final GoblinNegotiationEffect effect) {
        super(effect);
    }

    @Override
    public GoblinNegotiationEffect copy() {
        return new GoblinNegotiationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(
                CardUtil.getSourceCostsTag(game, source, "X", 0), source, game
        );
        if (excess > 0) {
            new GoblinToken().putOntoBattlefield(excess, game, source);
        }
        return true;
    }
}
