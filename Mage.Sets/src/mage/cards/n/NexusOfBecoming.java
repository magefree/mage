package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NexusOfBecoming extends CardImpl {

    public NexusOfBecoming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of combat on your turn, draw a card. Then you may exile an artifact or creature card from your hand. If you do, create a token that's a copy of the exiled card, except it's a 3/3 Golem artifact creature in addition to its other types.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new NexusOfBecomingEffect());
        this.addAbility(ability);
    }

    private NexusOfBecoming(final NexusOfBecoming card) {
        super(card);
    }

    @Override
    public NexusOfBecoming copy() {
        return new NexusOfBecoming(this);
    }
}

class NexusOfBecomingEffect extends OneShotEffect {

    NexusOfBecomingEffect() {
        super(Outcome.Benefit);
        staticText = "then you may exile an artifact or creature card from your hand. " +
                "If you do, create a token that's a copy of the exiled card, " +
                "except it's a 3/3 Golem artifact creature in addition to its other types.";
    }

    private NexusOfBecomingEffect(final NexusOfBecomingEffect effect) {
        super(effect);
    }

    @Override
    public NexusOfBecomingEffect copy() {
        return new NexusOfBecomingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(
                0, 1, StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE
        );
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 1, false,
                false, null, 3, 3, false
        ).setBecomesArtifact(true)
                .withAdditionalSubType(SubType.GOLEM)
                .setSavedPermanent(new PermanentCard(card, source.getControllerId(), game))
                .apply(game, source);
    }
}
