package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * "When a [filter] enters, you may return this card from your graveyard to the battlefield attached to that creature"
 *
 * @author xenohedron
 */
public class EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    public EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(FilterPermanent filter) {
        super(Zone.GRAVEYARD, new ReturnSourceAttachedToItEffect(), filter, true, SetTargetPointer.PERMANENT);
        setTriggerPhrase("When " + CardUtil.addArticle(filter.getMessage()) + " enters, ");
    }

    protected EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(final EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility copy() {
        return new EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(this);
    }
}

class ReturnSourceAttachedToItEffect extends OneShotEffect {

    ReturnSourceAttachedToItEffect() {
        super(Outcome.Benefit);
        staticText = "return this card from your graveyard to the battlefield attached to that creature";
    }

    private ReturnSourceAttachedToItEffect(final ReturnSourceAttachedToItEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSourceAttachedToItEffect copy() {
        return new ReturnSourceAttachedToItEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = source.getSourceCardIfItStillExists(game);
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard != null && permanent != null && controller != null) {
            game.getState().setValue("attachTo:" + sourceCard.getId(), permanent);
            if (controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game)) {
                permanent.addAttachment(sourceCard.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}
