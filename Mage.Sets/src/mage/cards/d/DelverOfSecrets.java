package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Alvin
 */
public final class DelverOfSecrets extends TransformingDoubleFacedCard {

    public DelverOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{U}",
                "Insectile Aberration",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.INSECT}, "U"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 2);

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DelverOfSecretsEffect(), TargetController.YOU, false
        ));

        // Insectile Aberration
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

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public DelverOfSecretsEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. You may reveal that card. " +
                "If an instant or sorcery card is revealed this way, transform {this}";
    }

    public DelverOfSecretsEffect(final DelverOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public DelverOfSecretsEffect copy() {
        return new DelverOfSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        player.lookAtCards(source, null, cards, game);
        if (!player.chooseUse(Outcome.DrawCard, "Reveal the top card of your library?", source, game)) {
            return false;
        }
        player.revealCards(source, cards, game);
        if (!card.isInstantOrSorcery(game)) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.transform(source, game);
    }
}
