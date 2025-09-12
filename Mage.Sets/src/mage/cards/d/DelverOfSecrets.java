package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
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
public final class DelverOfSecrets extends CardImpl {

    public DelverOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.i.InsectileAberration.class;

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DelverOfSecretsEffect()));
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

    public DelverOfSecretsEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. You may reveal that card. " +
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
