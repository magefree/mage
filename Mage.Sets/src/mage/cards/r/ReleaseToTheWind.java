
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

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
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                if (controller.moveCards(targetPermanent, Zone.EXILED, source, game)) {
                    Card card = game.getCard(targetPermanent.getId());
                    if (card != null) {
                        ContinuousEffect effect = new ReleaseToTheWindEffectCastFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class ReleaseToTheWindEffectCastFromExileEffect extends AsThoughEffectImpl {

    public ReleaseToTheWindEffectCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    public ReleaseToTheWindEffectCastFromExileEffect(final ReleaseToTheWindEffectCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ReleaseToTheWindEffectCastFromExileEffect copy() {
        return new ReleaseToTheWindEffectCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID ownerId = game.getOwnerId(objectId);
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(ownerId)) {
                Card card = game.getCard(objectId);
                Player player = game.getPlayer(ownerId);
                if (player != null && card != null) {
                    player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                    return true;
                }
            }
        } else {
            if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
                // object has moved zone so effect can be discarted
                this.discard();
            }
        }
        return false;
    }
}
