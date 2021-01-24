
package mage.cards.r;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ReleaseToTheWind extends CardImpl {

    public ReleaseToTheWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        getSpellAbility().addEffect(new ReleaseToTheWindEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());

    }

    public ReleaseToTheWind(final ReleaseToTheWind card) {
        super(card);
    }

    @Override
    public ReleaseToTheWind copy() {
        return new ReleaseToTheWind(this);
    }
}

class ReleaseToTheWindEffect extends OneShotEffect {

    public ReleaseToTheWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    public ReleaseToTheWindEffect(final ReleaseToTheWindEffect effect) {
        super(effect);
    }

    @Override
    public ReleaseToTheWindEffect copy() {
        return new ReleaseToTheWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && targetPermanent != null && sourceObject != null) {
            UUID exileId = CardUtil.getExileZoneId(
                    controller.getId().toString()
                            + "-" + game.getState().getTurnNum()
                            + "-" + sourceObject.getIdName(), game
            );
            String exileName = sourceObject.getIdName() + " free play for " + controller.getName();
            if (controller.moveCardsToExile(targetPermanent, source, game, true, exileId, exileName)) {
                Card card = game.getCard(targetPointer.getFirst(game, source));
                if (card != null) {
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.OWNER, Duration.Custom, true);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }
}
