
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.EldraziScionToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public final class BlightHerder extends CardImpl {

    public BlightHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}");
        this.subtype.add(SubType.ELDRAZI, SubType.PROCESSOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When you cast Blight Herder, you may put two cards your opponents own from exile into their owners' graveyards. If you do, create three 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        this.addAbility(new CastSourceTriggeredAbility(new BlightHerderEffect(), true));
    }

    private BlightHerder(final BlightHerder card) {
        super(card);
    }

    @Override
    public BlightHerder copy() {
        return new BlightHerder(this);
    }
}

class BlightHerderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public BlightHerderEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, create three 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}.";
    }

    public BlightHerderEffect(final BlightHerderEffect effect) {
        super(effect);
    }

    @Override
    public BlightHerderEffect copy() {
        return new BlightHerderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getControllerId(), source, game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, Zone.GRAVEYARD, source, game);
                    return new CreateTokenEffect(new EldraziScionToken(), 3).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
