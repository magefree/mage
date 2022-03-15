package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class GraveExchange extends CardImpl {

    public GraveExchange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Target player sacrifices a creature.
        this.getSpellAbility().addEffect(new GraveExchangeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GraveExchange(final GraveExchange card) {
        super(card);
    }

    @Override
    public GraveExchange copy() {
        return new GraveExchange(this);
    }
}

class GraveExchangeEffect extends OneShotEffect {

    public GraveExchangeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices a creature";
    }

    public GraveExchangeEffect(final GraveExchangeEffect effect) {
        super(effect);
    }

    @Override
    public GraveExchangeEffect copy() {
        return new GraveExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player == null) {
            return false;
        }

        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), player.getId(), game)
                && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                return permanent.sacrifice(source, game);
            }
        }
        return false;
    }
}
