
package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DelverOfSecretsEffect(), TargetController.YOU, false));
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
        this.staticText = "look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform {this}";
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
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null || sourcePermanent == null) {
            return false;
        }
        if (player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            if(card == null){
                return false;
            }
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards(sourcePermanent.getName(), cards, game);
            if (player.chooseUse(Outcome.DrawCard, "Reveal the top card of your library?", source, game)) {
                player.revealCards(sourcePermanent.getName(), cards, game);
                if (filter.match(card, game)) {
                    return new TransformSourceEffect().apply(game, source);
                }
            }

        }
        return true;
    }
}
