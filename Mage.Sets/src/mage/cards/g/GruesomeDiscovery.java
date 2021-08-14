package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author North
 */
public final class GruesomeDiscovery extends CardImpl {

    public GruesomeDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Target player discards two cards.
        // <i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, you choose two cards from it, then that player discards those cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GruesomeDiscoveryEffect(), new DiscardTargetEffect(2),
                MorbidCondition.instance, "Target player discards two cards. " +
                "<br><i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, " +
                "you choose two cards from it, then that player discards those cards"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private GruesomeDiscovery(final GruesomeDiscovery card) {
        super(card);
    }

    @Override
    public GruesomeDiscovery copy() {
        return new GruesomeDiscovery(this);
    }
}

class GruesomeDiscoveryEffect extends OneShotEffect {

    GruesomeDiscoveryEffect() {
        super(Outcome.Discard);
    }

    private GruesomeDiscoveryEffect(final GruesomeDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeDiscoveryEffect copy() {
        return new GruesomeDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player == null || targetPlayer == null) {
            return false;
        }
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);
        if (targetPlayer.getHand().size() <= 2) {
            targetPlayer.discard(2, false, false, source, game);
        }
        TargetCard target = new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS);
        player.choose(Outcome.Discard, targetPlayer.getHand(), target, game);
        targetPlayer.discard(new CardsImpl(target.getTargets()), false, source, game);
        return true;
    }
}
