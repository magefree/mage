package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Alvin
 */
public final class DelverOfSecrets extends TransformingDoubleFacedCard {

    public DelverOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{U}",
                "Insectile Aberration",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.INSECT}, "U");

        // Delver of Secrets
        this.getLeftHalfCard().setPT(1, 1);

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new DelverOfSecretsEffect()));

        // Insectile Aberration
        this.getRightHalfCard().setPT(3, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private DelverOfSecrets(final DelverOfSecrets card) {
        super(card);
    }

    @Override
    public DelverOfSecrets copy() {
        return new DelverOfSecrets(this);
    }
}

class DelverOfSecretsEffect extends OneShotEffect {
    DelverOfSecretsEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may reveal that card. " +
                "If an instant or sorcery card is revealed this way, transform {this}";
    }

    private DelverOfSecretsEffect(final DelverOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public DelverOfSecretsEffect copy() {
        return new DelverOfSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        player.lookAtCards(CardUtil.getSourceLogName(game, source), cards, game);
        if (!player.chooseUse(Outcome.DrawCard, "Reveal the top card of your library?", source, game)) {
            return false;
        }
        player.revealCards(source, cards, game);
        if (card.isInstantOrSorcery(game)) {
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                    .ifPresent(permanent -> permanent.transform(source, game));
        }
        return true;
    }
}
